package gonk;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommandHandler {
    private final MessageChannel channel;
    private final String message;

    CommandHandler(MessageReceivedEvent messageReceivedEvent) {
        this.channel = messageReceivedEvent.getChannel();
        this.message = messageReceivedEvent.getMessage().getContentDisplay();
    }

    public void sendMessage(String msg) {
        if (msg.length() >= 2000) {
            String end = "\nMsg too long...";
            msg = msg.substring(0, 2000 - (end.length() + 1));
            msg += end;
        }
        System.out.println(new Date() + ": " + msg);
        channel.sendMessage(msg).queue();
    }

    public void gonk() {
        GonkBot.addGonkChannel(channel);
        System.out.println("Gonking");
        System.out.println();
        int count = StringUtils.countMatches(message.toLowerCase(), "gonk");
        sendMessage(GonkBot.randomGonks(count));
    }

    public void daysTilBobaFett() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,11);
        cal.set(Calendar.DAY_OF_MONTH, 29);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        long days = ChronoUnit.DAYS.between(Instant.now(), cal.toInstant());
        StringBuilder sb = new StringBuilder("**");
        for (int i = 0; i < days; i++) {
            sb.append("GONK ");
        }
        sb.append("**");
        sendMessage(sb.toString());
    }

}
