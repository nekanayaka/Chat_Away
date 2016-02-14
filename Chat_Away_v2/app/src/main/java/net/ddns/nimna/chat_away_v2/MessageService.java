package net.ddns.nimna.chat_away_v2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.List;

/**
 * Created by Chris on 2016-02-05.
 */
public class MessageService extends Service implements SinchClientListener {

    private static final String APP_KEY = "75af48ff-56df-4fa5-a08a-8d8cdedd0a02";
    private static final String APP_SECRET = "+iwPgqZSpEan+HpWYuLrmA==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private final MessageServiceInterface serviceInterface = new MessageServiceInterface();
    private SinchClient sinchClient = null;
    private MessageClient messageClient = null;
    private String currentUserId;
    private Intent broadcastIntent = new Intent("net.ddns.nimna.chat_away_v2.ProfileActivity");
    private LocalBroadcastManager broadCaster;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get the current user id from Parse


        currentUserId = intent.getStringExtra("username");

        if (currentUserId != null && !isSinchClientStarted()) {
            startSinchClient(currentUserId);
        }
        Log.d("onStartCommand", "onStartCommand");

        broadCaster = LocalBroadcastManager.getInstance(this);

        return super.onStartCommand(intent, flags, startId);
    }
    public void startSinchClient(String username) {
        Log.d("startSinchClient", "startSinchClient");
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(username)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .build();
        //this client listener requires that you define
        //a few methods below
        sinchClient.addSinchClientListener(this);
        //messaging is "turned-on", but calling is not
        sinchClient.setSupportMessaging(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.checkManifest();
        sinchClient.start();
    }
    private boolean isSinchClientStarted() {
        return sinchClient != null && sinchClient.isStarted();
    }
    //The next 5 methods are for the sinch client listener
    @Override
    public void onClientFailed(SinchClient client, SinchError error) {
        broadcastIntent.putExtra("success", false);
        broadCaster.sendBroadcast(broadcastIntent);
        sinchClient = null;
    }
    @Override
    public void onClientStarted(SinchClient client) {
        Log.d("onClientStarted", "onClientStarted");
        broadcastIntent.putExtra("success", true);
        broadCaster.sendBroadcast(broadcastIntent);

        client.startListeningOnActiveConnection();
        messageClient = client.getMessageClient();
    }
    @Override
    public void onClientStopped(SinchClient client) {
        sinchClient = null;
    }
    @Override
    public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration clientRegistration) {}
    @Override
    public void onLogMessage(int level, String area, String message) {}
    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }
    public void sendMessage(String recipientUserId, String textBody) {
        if (messageClient != null) {
            WritableMessage message = new WritableMessage(recipientUserId, textBody);
            messageClient.send(message);
        }
    }
    public void sendGroupMessage(List<String> recipientUserId, String textBody) {
        if (messageClient != null) {
            WritableMessage message = new WritableMessage(recipientUserId, textBody);
            messageClient.send(message);
        }
    }
    public void addMessageClientListener(MessageClientListener listener) {
        if (messageClient != null) {
            messageClient.addMessageClientListener(listener);
        }
    }
    public void removeMessageClientListener(MessageClientListener listener) {
        if (messageClient != null) {
            messageClient.removeMessageClientListener(listener);
        }
    }
    @Override
    public void onDestroy() {
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
    }
    //public interface for ListUsersActivity & MessagingActivity
    public class MessageServiceInterface extends Binder {
        public void sendMessage(String recipientUserId, String textBody) {
            MessageService.this.sendMessage(recipientUserId, textBody);
        }
        public void sendGroupMessage(List<String> recipientUserId, String textBody) {
            MessageService.this.sendGroupMessage(recipientUserId, textBody);
        }
        public void addMessageClientListener(MessageClientListener listener) {
            MessageService.this.addMessageClientListener(listener);
        }
        public void removeMessageClientListener(MessageClientListener listener) {
            MessageService.this.removeMessageClientListener(listener);
        }
        public boolean isSinchClientStarted() {
            return MessageService.this.isSinchClientStarted();
        }
    }
}
