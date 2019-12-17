import pymysql.cursors
import smtplib
import sys
from string import Template
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


MY_ADDRESS = None
PASSWORD = None

def main():
    db = pymysql.connect(host='cs-database.cs.loyola.edu', user='cnmeier', password='1732821', db='sarepach')
    cursor = db.cursor(pymysql.cursors.DictCursor)
    cursor.execute("SELECT email FROM user")
    data = cursor.fetchall()
    print(data)
    print("test")
    s = smtplib.SMTP(host='smtp-mail.outlook.com', port=587)
    s.starttls()
    MY_ADDRESS = sys.argv[1]
    PASSWORD = sys.argv[2]
    s.login(MY_ADDRESS, PASSWORD)
    for userdata in data:
        for useremail in userdata.values():
            try:
           #     sql = "select * from bidsOn where email = 'useremail' and datetime in (select max(datetime) from bidsOn where email = 'useremail' group by email, id)"
                sql = "SELECT * from bidsOn"
                result = cursor.execute(sql);
                # Check to see if the user bidded on at least one item
                print(result)
                if (cursor.rowcount > 0):
                    row = cursor.fetch_assoc()
                    while(row is not None):
                        id = row['id']
                        itemInfo = cursor.execute("select * from item where id = 'id'")
                        itemInfo = cursor.fetch_assoc()
                        # Retrieve the highest bidder for each item
                        topBid = cursor.execute("select * from bidsOn where id = 'id' and amount = (select max(amount) from bidsOn where id = 'id' group by id)")
                        topBid = cursor.fetch_assoc()
                        if (topBid['email'] == useremail):
                            isTopBid = True
                        else:
                            isTopBid = False
                        msg = MIMEMultipart()
                        msg['From'] = MY_ADDRESS
                        msg['To']= useremail
                        print(useremail)
                        msg['Subject']="You're losing on a bid!"
                        message = itemInfo
                        msg.attach(MIMEText(message, 'plain'))
                        if(isTopBid):
                            s.send_message(msg)
                        del msg
                        row = cursor.fetch_assoc()
            except Exception as e:
                print(str(e) + useremail)
                pass
