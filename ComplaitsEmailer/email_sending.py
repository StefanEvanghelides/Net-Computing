from smtplib import SMTP


def send_email_about_complaints(server, from_email, password, to_email, complaints):
    from email.mime.text import MIMEText

    body = "\r\n Hi there,\n\nNew complaints are available: \n\n"

    for complaint in complaints:
        description = complaint["description"]
        type = complaint["type"]
        body = body + "[" + str(type) + "] -- " + str(description) + "\n"

    body = body + "\nGood luck with solving\n\nBest,\nThe Complaining Team\r\n"

    message = MIMEText(body)
    message['From'] = from_email
    message['To'] = to_email
    message['Subject'] = 'New complaints available'

    server = SMTP(server)
    server.ehlo()
    server.starttls()
    server.login(user=from_email, password=password)
    server.sendmail(from_addr=from_email, to_addrs=to_email, msg=str(message))
    server.quit()