package gonk;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class EventHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            if (event.getAuthor().isBot()) return;
            chooseCommand(event);
        } catch (IOException e) {
//            System.out.println("Failed to connect to API");
            e.printStackTrace();
        }
    }

    private void chooseCommand(MessageReceivedEvent event) throws IOException {
        String message = event.getMessage().getContentDisplay().toLowerCase();
        CommandHandler commandHandler = new CommandHandler(event);

        if (message.contains("gonk")) {
            commandHandler.gonk();
        } else if (message.startsWith("!bobafett") || message.startsWith("!daystobobafett")) {
            commandHandler.daysTilBobaFett();
        }
    }

}
