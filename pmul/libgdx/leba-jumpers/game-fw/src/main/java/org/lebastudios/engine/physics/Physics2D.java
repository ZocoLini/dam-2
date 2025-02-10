package org.lebastudios.engine.physics;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Physics2D
{
    @Getter @Setter private static float gravity = 9.8f;
    @Getter private static CollisionMatrix collisionMatrix;

    public static class CollisionMatrix
    {
        private List<List<Boolean>> matrix = new ArrayList<>(new ArrayList<>());
        private LinkedHashSet<String> layers = new LinkedHashSet<>();

        public CollisionMatrix(int size)
        {
            addLayer("Default");
        }

        public void addLayer(String layer)
        {
            layers.add(layer);
        }

        public void setCollision(int layer1, int layer2, boolean value)
        {

        }

        public boolean canCollide(String layer1, String layer2)
        {
            return false;
        }

        public boolean canCollide(int layer1, int layer2)
        {
            return matrix.get(layer1).get(layer2);
        }
    }
}
