package nl.imine.elytraboost;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.bukkit.Location;
import org.bukkit.World;

public class TestExclStrat implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaringClass() != ElytraBooster.class && f.getDeclaringClass() != Location.class && f.getDeclaringClass() != World.class;
    }

}
