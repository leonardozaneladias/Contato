package br.com.caelum.contato;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] msgs = (Object[]) bundle.get("pdus");
        byte[] msg = (byte[]) msgs[0];
        String format = (String) bundle.get("format");
        SmsMessage sms = SmsMessage.createFromPdu(msg, format);
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.isAluno(sms.getDisplayOriginatingAddress())){
            Toast.makeText(context,"SMS de Aluno" + sms.getDisplayMessageBody(), Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
    }
}
