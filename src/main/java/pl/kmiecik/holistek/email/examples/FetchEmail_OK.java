package pl.kmiecik.holistek.email.examples;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * https://www.tutorialspoint.com/javamail_api/javamail_api_fetching_emails.htm
 */
public class FetchEmail_OK {

    public static void fetch(String myHost, String storeType, String user,
                             String password) {
        try {
            // create properties field
            Properties properties = getProperties(myHost);
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            //    Store store = emailSession.getStore("pop3s");
            Store store = emailSession.getStore(storeType);

            store.connect(myHost, user, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE); // orginally it was READ_ONLY but  it was changed due to  write "SEEN MAIL" flag

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));


            // search for all "unseen" messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message messages[] = emailFolder.search(unseenFlagTerm);


            // retrieve ALL messages from the folder in an array and print it
            //Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                emailFolder.setFlags(new Message[]{message}, new Flags(Flags.Flag.SEEN), true); //change flag to "seen mail"
                System.out.println("---------------------------------");
                writePart(message);
                String line = reader.readLine();
                if ("YES".equals(line)) {
                    message.writeTo(System.out);
                } else if ("QUIT".equals(line)) {
                    break;
                }
            }

            // close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties getProperties(String myHost) {
        Properties properties = new Properties();
        if (myHost.equals("pop.gmail.com")) {
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", myHost);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
        }
        if (myHost.equals("imap.gmail.com")) {
            properties.put("mail.store.protocol", "imap");
            properties.put("mail.imap.host", myHost);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
        }
   /*     properties.put("mail.protocol.proxy.host", "proxyrrnl.delphiauto.net");
        properties.put("mail.protocol.proxy.port", "8080");
        properties.put("mail.protocol.proxy.user", "");
        properties.put("mail.protocol.proxy.password", "");*/


        return properties;
    }

    public static void main(String[] args) {

        String username = "holistech.gdansk@gmail.com";// change accordingly
        String password = "mp3!";// change accordingly



        //POP
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3s";


        //IMAP
      /*  String host = "imap.gmail.com";// change accordingly
        String mailStoreType = "imaps";*/

        fetch(host, mailStoreType, username, password);


    }

    /*
     * This method checks for content-type
     * based on which, it processes and
     * fetches the content of the message
     */
    public static void writePart(Part p) throws Exception {
        if (p instanceof Message)
            //Call methos writeEnvelope
            writeEnvelope((Message) p);

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i));
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            writePart((Part) p.getContent());
        }
        //check if the content is an inline image
        /*else if (p.isMimeType("image/jpeg")) {
            System.out.println("--------> image/jpeg");
            Object o = p.getContent();

            InputStream x = (InputStream) o;
            // Construct the required byte array
            System.out.println("x.length = " + x.available());
            while ((i = (int) ((InputStream) x).available()) > 0) {
                int result = (int) (((InputStream) x).read(bArray));
                if (result == -1)
                    int i = 0;
                byte[] bArray = new byte[x.available()];

                break;
            }
            FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
            f2.write(bArray);
        }*/
        else if (p.getContentType().contains("image/")) {
            System.out.println("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test =
                    (com.sun.mail.util.BASE64DecoderStream) p
                            .getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
            } else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1)
                    System.out.write(c);
            } else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }

    }

    /*
     * This method would print FROM,TO and SUBJECT of the message
     */
    public static void writeEnvelope(Message m) throws Exception {
        System.out.println("This is the message envelope");
        System.out.println("---------------------------");
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("FROM: " + a[j].toString());
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("TO: " + a[j].toString());
        }

        // SUBJECT
        if (m.getSubject() != null)
            System.out.println("SUBJECT: " + m.getSubject());

    }
}
