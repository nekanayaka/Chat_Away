package net.ddns.nimna.chat_away_v2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.List;

/**
 * Created by Chris on 2016-01-22.
 */

public class MessagingActivity extends Activity {

    private String recipientId;
    private EditText messageBodyField;
    private String messageBody;
    private MessageService.MessageServiceInterface messageService;
    private String currentUserId;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private ListView messagesList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
        //binding messaging service
        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);

        //getting the logged in userID and the recipients ID from the Intent
        Bundle extra = getIntent().getExtras();
        currentUserId = extra.getString("username");
        recipientId = extra.getString("RECIPIENT_ID");
        Log.d("USER_MESSAGE_ACTIVITY", "-->"+currentUserId);

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);
        messagesList = (ListView) findViewById(R.id.listMessages);
        messageAdapter = new MessageAdapter(this);
        messagesList.setAdapter(messageAdapter);


        //listen for a click on the send button
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the send button is clicked the text entered is retrieved
                messageBody = messageBodyField.getText().toString();
                //if message is empty the user is displayed an error
                if (messageBody.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("MESSAGE_BODY", "-->" + messageBody);
                Log.d("RECIPIENT_ID","-->"+recipientId);
                //the MessageService sendMessage is called which will send the message to the sinch client
                messageService.sendMessage(recipientId, messageBody);
                messageBodyField.setText("");
            }
        });
    }

    @Override
    public void onDestroy() {
        //service is unbinded when the activity is destroyed
        unbindService(serviceConnection);
        super.onDestroy();
    }

    /**
     * This class is used for setting up the messaging service and adding the required Listeners
     */
    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SERVICE_CONNECTED", "Service is connected");
            messageService = (MessageService.MessageServiceInterface) iBinder;
            MessageClientListener messageClientListener = new MyMessageClientListener();
            messageService.addMessageClientListener(messageClientListener);
            messageService.sendMessage(recipientId, "Connected");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messageService = null;
        }
    }

    /**
     * This class included all the methods used for the message Listener
     */
    private class MyMessageClientListener implements MessageClientListener {
        //Notify the user if their message failed to send
        @Override
        public void onMessageFailed(MessageClient client, Message message,
                                    MessageFailureInfo failureInfo) {
            Log.d("FAILURE_INFO", "-->"+failureInfo.toString());
            Toast.makeText(MessagingActivity.this, "Message failed to send.", Toast.LENGTH_LONG).show();

        }
        //This method is not needed for our application
        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {

        }

        //An incoming message is turned into a WritableMessage object and the message is displayed on the messaging page via the adapter
        @Override
        public void onIncomingMessage(MessageClient client, Message message) {
            //Display an incoming message
            if (message.getSenderId().equals(recipientId)) {
                WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
                messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_INCOMING);
            }
        }
        //When the user sends a message it is added as a WritableMessage and displayed on the messing page as an outgoing message
        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {
            WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
            messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_OUTGOING);
        }

        //Method not currently being used by the application
        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }
}
