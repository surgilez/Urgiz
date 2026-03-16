/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioTipoAmbiente;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeUtility;

/**
 * Clase que permite el envio de e-mails utilizando el API javamail.
 *
 */
public class MailerClassSistema {

    private Tipoambiente amb = new Tipoambiente();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();

    /**
     * Recupera el nombre del catálogo descrito en la enumeración
     *
     * @param categoria nombre del parametroa a buscar
     * @return
     */
    public String getConfiguracionCorreo(String categoria) {
//        Set<BeCatalogo> dato = ofertaServicio.getCatalogo1(categoria);
//        if (dato.iterator().hasNext()) {
//            return dato.iterator().next().getNbCatalogo();
//        }
        return null;
    }

    /**
     * Método que envía al mail las credenciales de acceso al sistema
     *
     * @param address Dirección de correo electronico
     * @param mensaje Contenido del mensaje
     * @return
     * @throws java.rmi.RemoteException
     */
    class SmtpAuthenticator extends Authenticator {

        public SmtpAuthenticator() {

            super();
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
//            amb = servicioTipoAmbiente.FindALlTipoambiente();
//            String username = amb.getAmUsuarioSmpt().trim();
//            String password = amb.getAmPassword().trim();
            String username = "qbs@intersys-it.com";
            String password = "TCsender2021$";
            return new PasswordAuthentication(username, password);

        }
    }

    /*MAIL RECUPERA CONTRASEÑA*/
    public boolean sendMailRecuperarPassword(String address,
            String asuntoInf, String usuarioRecup,
            String passwordRecup, Tipoambiente ambiente)
            throws java.rmi.RemoteException {

        try {
//                        String usuarioSmpt = "deckxelec@gmail.com";
//            String password = "metalicas366";

            amb = ambiente;

            String asunto = asuntoInf;
            String host = "smtp.office365.com";
            String port = "587";
            String protocol = "smtp";
            String usuarioSmpt = "qbs@intersys-it.com";
            String password = "TCsender2021$";

            // Propiedades de la conexión
            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.user", usuarioSmpt);
            properties.setProperty("mail.smtp.password", password);
            properties.setProperty("mail.smtp.port", port);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.debug", "false");
            // Setup Port
            properties.put("mail.smtp.ssl.trust", host);
            SmtpAuthenticator auth = new SmtpAuthenticator();
            // Get the default Session object.
            Session session = Session.getInstance(properties, auth);
            MimeMessage m = new MimeMessage(session);
            String nickFrom = MimeUtility.encodeText("Facturas.ec");
//            String nickTo = MimeUtility.encodeText(amb.getAmNombreComercial());
            Address addressfrom = new InternetAddress(usuarioSmpt, nickFrom);

            m.setFrom(addressfrom);

            BodyPart texto = new MimeBodyPart();
            String HTMLENVIO = "<body>\n"
                    + "    <img alt=\"logo\" style=\"height:100px;margin:auto;display:block\"\n"
                    + "        src=\"https://i.ibb.co/hH54V9F/logoh.png\" />\n"
                    + "    <div style=\"height: 10px;background:#fbc620;\"></div>\n"
                    + "    <p style=\"margin-bottom: 0;\">Estimado (a) " + amb.getAmRazonSocial() + ",</p>\n"
                    + "    <p>Gracias por seleccionar FACTURAS.EC, Nos complace confirmar sus credenciales:</p>\n"
                    + "    <div style=\"height: 10px;background:#ffe79e;\"></div>\n"
                    + "    <p style=\"margin-bottom: 0;\"><strong>USUARIO:</strong>" + usuarioRecup + " </p>\n"
                    + "    <p style=\"margin-bottom: 0;\"><strong>PASSWORD:</strong>" + passwordRecup + "</p>\n"
                    + "    <br>\n"
                    + "    <div style=\"height: 10px;background:#ffe79e;\"></div>\n"
                    + "    <p>Si tiene cualquier duda o pregunta nos puede escribír al mail <a href=\"\">send@facturas.ec</a> o llámenos al\n"
                    + "        0992553708.</p>\n"
                    + "    <p>Saludos Cordiales,\n"
                    + "        <br>\n"
                    + "        Patrick Arch\n"
                    + "        <br>\n"
                    + "        Director\n"
                    + "    </p>\n"
                    + "\n"
                    + "    <div style=\"display: grid;\">\n"
                    + "        <div style=\"width: auto;margin:auto;\">\n"
                    + "            <p style=\"text-align: center;\">FACTURAS.EC\n"
                    + "                <br>\n"
                    + "                QUITO - ECUADOR\n"
                    + "            </p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "\n"
                    + "    <div style=\"height: 10px;background:black;\"></div>\n"
                    + "\n"
                    + "    <p style=\"font-weight: 900;color:#7f7f7f;\">Disclaimer</p>\n"
                    + "    <p  style=\"color:#7f7f7f;\">The information contained in this communication from the sender is confidential.\n"
                    + "        It is intended solely for use by the recipient and others authorized to receive it.\n"
                    + "        If you are not the recipient, you are hereby notified that any disclosure, copying,\n"
                    + "        distribution or taking action in relation of the contents of this information is strictly\n"
                    + "        prohibited and may be unlawful.</p>\n"
                    + "</body>";

            texto.setContent(HTMLENVIO, "text/html");

            MimeMultipart multiParte = new MimeMultipart();
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            multiParte.addBodyPart(texto);

//            m.setRecipients(Message.RecipientType.TO, addresTto);
//            m.setRecipients(Message.RecipientType.BCC, from);
            m.setSubject(asunto);
            m.setSentDate(new java.util.Date());
//             m.setContent(dirDatos, "text/plain");
            m.setContent(multiParte);

            Transport t = session.getTransport(protocol);
//             t.connect();
            t.connect(host, usuarioSmpt, password);
            t.send(m);
            t.close();
            return true;
        } catch (javax.mail.MessagingException e) {
            System.out.println("error" + e);
            e.printStackTrace();

            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailerClassSistema.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
