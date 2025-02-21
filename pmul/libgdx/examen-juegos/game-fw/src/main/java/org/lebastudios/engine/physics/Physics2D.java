package org.lebastudios.engine.physics;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Physics2D
{
    @Getter @Setter private static float gravity = 9.8f;
    @Getter private static final CollisionMatrix collisionMatrix = new CollisionMatrix();

    public static class CollisionMatrix
    {
        private boolean[][] matrix = new boolean[16][16];
        private final HashMap<String, Integer> layers = new HashMap<>();

        public CollisionMatrix()
        {
            addLayer("Default");
        }

        public void addLayer(String layer)
        {
            if (layers.containsKey(layer))
            {
                throw new IllegalArgumentException("Layer already exists");
            }

            int id = layers.size();
            layers.put(layer, layers.size());

            if (id >= matrix.length)
            {
                boolean[][] newMatrix = new boolean[id + 16][id + 16];
                for (int i = 0; i < matrix.length; i++)
                {
                    System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
                }
                matrix = newMatrix;
            }

            for (int i = 0; i < matrix.length; i++)
            {
                matrix[id][i] = false;
                matrix[i][id] = false;
            }
        }

        public void setCollision(String layer1, String layer2, boolean value)
        {
            setCollision(layers.get(layer1), layers.get(layer2), value);
        }

        public void setCollision(int layer1, int layer2, boolean value)
        {
            matrix[layer1][layer2] =  value;
            matrix[layer2][layer1] =  value;
        }

        public boolean canCollide(String layer1, String layer2)
        {
            return canCollide(layers.get(layer1), layers.get(layer2));
        }

        public boolean canCollide(int layer1, int layer2)
        {
            if (layer1 >= layers.size() || layer2 >= layers.size())
            {
                throw new IllegalArgumentException("Layer does not exist");
            }
            if (layer1 >= matrix.length || layer2 >= matrix.length)
            {
                throw new IllegalArgumentException("Matrix is not big enough");

            }

            return matrix[layer1][layer2];
        }
    }
}
