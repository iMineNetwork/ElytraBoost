package nl.imine.elytraboost;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;

public class ElytraBoosterManager {

    private static ElytraBoosterManager instance;
    private List<ElytraBooster> boosters = new ArrayList<>();

    List<String> options;

    private ElytraBoosterManager() {
        options = new ArrayList<>();
        options.add("cooldowntime");
        options.add("effectradius");
        options.add("location");
        options.add("name");
        options.add("power");
        options.add("range");
        options.add("requiresunlock");
    }   

    public static ElytraBoosterManager getInstance() {
        if (instance == null) {
            instance = new ElytraBoosterManager();
        }
        return instance;
    }

    public List<ElytraBooster> getBoosters() {
        if (boosters == null) {
            boosters = new ArrayList<>();
        }
        return boosters;
    }

    public List<String> getOptions() {
        return options;
    }

    public void saveBoosters() {
        if (boosters == null) {
            return;
        }
        FileWriter writer;
        try {
            File f = new File("ElytraBooster.json");
            if (!f.exists()) {
                f.createNewFile();
            }
            writer = new FileWriter(f);
            //Gson gson = new GsonBuilder().setExclusionStrategies(new TestExclStrat()).create();

            writer.write(new Gson().toJson(boosters.toArray(), ElytraBooster[].class));
            writer.close();
        } catch (Exception ex) {
            System.out.println("Couldn't save boosters, because: " + ex.getClass());
        }
    }

    public void loadBooster() {
        try {
            File f = new File("ElytraBooster.json");
            if (!f.exists()) {
                f.createNewFile();

            }
            //it's not possible to go directly to an ArrayList
            boosters = new ArrayList(Arrays.asList(new Gson().fromJson(new FileReader("ElytraBooster.json"), ElytraBooster[].class)));

        } catch (Exception ex) {
            System.out.println("Couldn't load boosters, because: " + ex.getClass());
            ex.printStackTrace();
        }
    }

    public void showAllLocations() {
        boosters.forEach(booster -> {
            booster.showLocation();
        });
    }

    public void createBooster(String name, Location location, boolean requiresUnlock, long cooldownTime, double effectRadius, float boostPower) {
        ElytraBooster booster = new ElytraBooster(name, location, requiresUnlock, cooldownTime, effectRadius, boostPower);
        System.out.println("1pizza");
        getBoosters().add(booster);
        System.out.println("2pizza");
        saveBoosters();
    }

    public static void main(String[] args) {
        ElytraBoosterManager.getInstance().getBoosters().add(new ElytraBooster("", new Location(null, 0, 0, 0), true, 0, 0, 0));
        ElytraBoosterManager.getInstance().getBoosters().add(new ElytraBooster("", new Location(null, 0, 0, 1), true, 0, 0, 0));
        ElytraBoosterManager.getInstance().getBoosters().add(new ElytraBooster("", new Location(null, 0, 1, 0), true, 0, 0, 0));
        ElytraBoosterManager.getInstance().getBoosters().add(new ElytraBooster("", new Location(null, 0, 1, 1), true, 0, 0, 0));
        ElytraBoosterManager.getInstance().getBoosters().add(new ElytraBooster("", new Location(null, 1, 0, 0), true, 0, 0, 0));
        ElytraBoosterManager.getInstance().saveBoosters();
        ElytraBoosterManager.getInstance().getBoosters().clear();
        ElytraBoosterManager.getInstance().loadBooster();
        System.out.println(ElytraBoosterManager.getInstance().getBoosters().size());
        System.out.println(ElytraBoosterManager.getInstance().boosters.get(0).getEffectRadius());
    }
}
