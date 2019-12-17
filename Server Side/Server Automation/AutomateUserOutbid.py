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
                msg = MIMEMultipart()
                msg['From'] = MY_ADDRESS
                msg['To']= useremail
                print(useremail)
                msg['Subject']="Baltimore Symphony Auction is Over"
                message = "The silent auction for the Baltimore Symphony has ended, thanks for taking part!"
                msg.attach(MIMEText(message, 'plain'))
                s.send_message(msg)
                del msg
            except Exception as e:
                print(str(e) + useremail)
                pass



if __name__=='__main__':
    main()
