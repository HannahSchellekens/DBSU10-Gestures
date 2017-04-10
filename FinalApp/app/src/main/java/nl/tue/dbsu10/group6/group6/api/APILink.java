package nl.tue.dbsu10.group6.group6.api;

import nl.tue.dbsu10.group6.gestures.Gestures;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.dbsu10.group6.group6.Util;
import nl.tue.dbsu10.group6.group6.command.CoffeeOutput;
import nl.tue.id.oocsi.OOCSIEvent;
import nl.tue.id.oocsi.client.OOCSIClient;
import nl.tue.id.oocsi.client.protocol.EventHandler;

/**
 * @author Ruben Schellekens
 */
public class APILink {

    private final MainActivity main;
    private final OOCSIClient client;

    private final CoffeeApi coffeeApi = new CoffeeApi();

    public APILink(MainActivity main) {
        this.main = main;
        this.client = Gestures.getClient();
    }

    public void setup() {
        setupCoffee();
        setupPizza();
    }

    private void setupPizza() {
        client.subscribe("choosePizza", new EventHandler() {
            @Override
            public void receive(OOCSIEvent oocsiEvent) {
                if (!oocsiEvent.has("response")) {
                    return;
                }

                Util.toast(main, "Pizza: " + oocsiEvent.getString("response"));
            }
        });
    }

    private void setupCoffee() {
        client.subscribe(CoffeeOutput.CHANNEL, new EventHandler() {
            @Override
            public void receive(OOCSIEvent oocsiEvent) {
                int orderId = oocsiEvent.getInt("number", -1);
                if (!coffeeApi.getOrdered().contains(orderId)) {
                    return;
                }

                int status = oocsiEvent.getInt("output_type", -1);
                if (status == -1) {
                    return;
                }

                // Cannot be confirmed
                if (orderId == 2) {
                    Util.toast(main, "Coffee could not be confirmed");
                    coffeeApi.getOrdered().remove(orderId);
                }
                // Coffee prepared
                if (orderId == 3) {
                    Util.toast(main, "Coffee is being prepared");
                }
                // Ready
                if (orderId == 4) {
                    Util.toast(main, "Coffee is ready");
                    coffeeApi.getOrdered().remove(orderId);
                }
                // Removed
                if (orderId == 7) {
                    Util.toast(main, "The Coffee has been removed");
                    coffeeApi.getOrdered().remove(orderId);
                }
            }
        });
    }

    public CoffeeApi getCoffeeApi() {
        return coffeeApi;
    }
}
