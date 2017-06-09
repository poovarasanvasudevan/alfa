package com.poovarasan.afka.core;

import android.content.Context;
import android.util.Log;

import com.poovarasan.afka.receiver.RosterListener;
import com.poovarasan.afka.service.XMPPChatManagerListener;
import com.poovarasan.afka.service.XMPPChatMessageListener;
import com.poovarasan.afka.service.XMPPConnectionListener;
import com.poovarasan.afka.ssl.Factory;

import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by poovarasanv on 26/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 26/5/17 at 9:08 AM
 */

public class XMPPFactory {
    private static XMPPTCPConnection xmpptcpConnection = null;

    public static XMPPTCPConnection getXmpptcpConnection(Context context) throws XmppStringprepException, NoSuchAlgorithmException, KeyManagementException {
        if (xmpptcpConnection == null) {
            xmpptcpConnection = new XMPPTCPConnection(xmpptcpConnectionConfiguration(context));

            XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true);
            XMPPTCPConnection.setUseStreamManagementDefault(true);
            xmpptcpConnection.setPreferredResumptionTime(10000);
            xmpptcpConnection.setPacketReplyTimeout(100000);
            xmpptcpConnection.addConnectionListener(new XMPPConnectionListener());
            xmpptcpConnection.addAsyncStanzaListener(new XMPPChatMessageListener(), new StanzaTypeFilter(Message.class));


            ReconnectionManager.getInstanceFor(xmpptcpConnection).enableAutomaticReconnection();
            ServerPingWithAlarmManager.onCreate(context);

            ServerPingWithAlarmManager.getInstanceFor(xmpptcpConnection).setEnabled(true);
            ReconnectionManager.setEnabledPerDefault(true);

            ChatManager.getInstanceFor(xmpptcpConnection)
                    .addOutgoingListener(new OutgoingChatMessageListener() {
                        @Override
                        public void newOutgoingMessage(EntityBareJid to, Message message, Chat chat) {

                        }
                    });

            ChatManager.getInstanceFor(xmpptcpConnection)
                    .addIncomingListener(new IncomingChatMessageListener() {
                        @Override
                        public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {

                        }
                    });

            org.jivesoftware.smack.chat.ChatManager.getInstanceFor(xmpptcpConnection)
                    .addChatListener(new XMPPChatManagerListener());

            Roster.getInstanceFor(xmpptcpConnection).addRosterListener(new RosterListener());
            Roster.getInstanceFor(xmpptcpConnection).setSubscriptionMode(Roster.SubscriptionMode.accept_all);

            PingManager pingManager = PingManager.getInstanceFor(xmpptcpConnection);
            pingManager.setPingInterval(60);


            ReconnectionManager manager = ReconnectionManager.getInstanceFor(xmpptcpConnection);
            manager.enableAutomaticReconnection();


            FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(xmpptcpConnection);
            fileTransferManager.addFileTransferListener(new FileTransferListener() {
                @Override
                public void fileTransferRequest(FileTransferRequest request) {
                    IncomingFileTransfer transfer = request.accept();
                    Log.i("ProgressBar", "Progress Sixe : " + transfer.getProgress());
                }
            });

        }


        return xmpptcpConnection;
    }

    public static XMPPTCPConnection connect(Context context, String username, String password) throws InterruptedException, XMPPException, SmackException, IOException, KeyManagementException, NoSuchAlgorithmException {

        XMPPTCPConnection xmpptcpConnection = XMPPFactory.getXmpptcpConnection(context);
        if (!xmpptcpConnection.isConnected()) {
            try {
                xmpptcpConnection.connect();
            } catch (SmackException.AlreadyConnectedException e) {
                Log.i("XMPP", "Client Already Connected");
            }
        }

        if (!xmpptcpConnection.isAuthenticated()) {
            if (Prefs.INSTANCE.contains("username") && Prefs.INSTANCE.contains("password")) {
                try {
                    xmpptcpConnection.login(Prefs.INSTANCE.getString("username", ""), Prefs.INSTANCE.getString("password", ""));
                } catch (SmackException.AlreadyLoggedInException e) {
                    Log.i("XMPP", "Client Already loged IN");
                }
            } else {
                xmpptcpConnection.login(username, password);

                if (xmpptcpConnection.isAuthenticated()) {
                    Prefs.INSTANCE.putString("username", username);
                    Prefs.INSTANCE.putString("password", password);
                }
            }
        }

        return xmpptcpConnection;
    }

    private static XMPPTCPConnectionConfiguration xmpptcpConnectionConfiguration(Context context) throws KeyManagementException, NoSuchAlgorithmException, XmppStringprepException {
        XMPPTCPConnectionConfiguration xmpptcpConnection1 = XMPPTCPConnectionConfiguration.builder()
                .setCompressionEnabled(true)
                .setXmppDomain("localhost")
                .setHost("10.0.2.2")
                .setDebuggerEnabled(true)
                .setConnectTimeout(10000000)
                .setCustomSSLContext(Factory.sslContext(context))
                .setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).build();


        return xmpptcpConnection1;
    }
}
