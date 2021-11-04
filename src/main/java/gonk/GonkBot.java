package gonk;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import okhttp3.Call;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GonkBot {
    private static final HashMap<String,MessageChannel> autoGonkChannels = new HashMap<>();
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config"));
        String token = prop.getProperty("token");
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new EventHandler());
        builder.build();
        System.out.println("Currently running");
        schedule();
    }

    public static void addGonkChannel(MessageChannel channel) {
        if (!autoGonkChannels.containsKey(channel.getName()))
            autoGonkChannels.put(channel.getName(), channel);
    }

    public static String randomGonk() {
        StringBuilder gonk = new StringBuilder("GONK");
        Random rand = new Random();
        int stars = rand.nextInt(3);
        if (rand.nextBoolean()) {
            gonk.append("!");
        }
        for (int i = 0; i < stars; i++) {
            gonk.insert(0,"*");
            gonk.append("*");
        }
        return gonk.toString();
    }

    public static String randomGonks(int count) {
        String gonk = randomGonk();
        StringBuilder sb = new StringBuilder(gonk);
        for (int i = 1; i < count; i++) {
            sb.append(" ").append(gonk);
        }
        return sb.toString();
    }

    private static void schedule() throws Exception {
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Callable<Void> task = new Callable<Void>() {
            public Void call() {
                System.out.println(autoGonkChannels.values().size());
                for (MessageChannel channel : autoGonkChannels.values()) {
                    channel.sendMessage(randomGonk()).queue();
                }
//                System.out.println(new Date());
                scheduler.schedule(this,new Random().nextInt(600)+240, TimeUnit.MINUTES);
                return null;
            }
        };

        int delay = 5;
        task.call();
        //scheduler.shutdown();

    }

}
