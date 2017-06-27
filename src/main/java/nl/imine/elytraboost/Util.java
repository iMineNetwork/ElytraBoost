/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost;

import org.bukkit.Location;

/**
 *
 * @author Dennis
 */
public class Util {

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static ElytraBooster getBooster(String given) {
        String name = "";
        int id = 0;
        boolean usingID = false;

        try {
            id = Integer.parseInt(given);
            usingID = true;
        } catch (NumberFormatException e) {
            name = given;
        }
        ElytraBooster booster = null;

        for (ElytraBooster elytraBooster : ElytraBoosterManager.getInstance().getBoosters()) {
            if (usingID ? elytraBooster.getID() == id : elytraBooster.getName().equals(name)) {
                booster = elytraBooster;
                break;
            }
        }
        return booster;
    }

    public static Location getRelativePosition(Location locationSender, String offsetX, String offsetY, String offsetZ) throws NumberFormatException {
        Location location = locationSender;

        if (offsetX.startsWith("~")) {
            if (!offsetX.equalsIgnoreCase("~")) {
                location.setX(location.getX() + Double.parseDouble(offsetX.substring(1)));
            }
        } else {
            location.setX(Double.parseDouble(offsetX));
        }

        if (offsetY.startsWith("~")) {
            if (!offsetY.equalsIgnoreCase("~")) {
                location.setY(location.getY() + Double.parseDouble(offsetY.substring(1)));
            }
        } else {
            location.setY(Double.parseDouble(offsetY));
        }

        if (offsetZ.startsWith("~")) {
            if (!offsetZ.equalsIgnoreCase("~")) {
                location.setZ(location.getZ() + Double.parseDouble(offsetZ.substring(1)));
            }
        } else {
            location.setZ(Double.parseDouble(offsetZ));
        }

        return location;
    }
}
