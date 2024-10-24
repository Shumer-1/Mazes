package backend.academy.solvers;

import backend.academy.elements.Cell;
import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraSolver implements Solver {

    @Override
    public List<Coordinates> solve(Maze maze, Coordinates start, Coordinates end) {
        int startY = start.y();
        int startX = start.x();
        int endY = end.y();
        int endX = end.x();

        PriorityQueue<Node> openList = new PriorityQueue<>();
        int[][] distances = new int[maze.height()][maze.width()];
        boolean[][] visited = new boolean[maze.height()][maze.width()];
        Node[][] parents = new Node[maze.height()][maze.width()];

        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        distances[startY][startX] = 0;
        openList.add(new Node(startY, startX, 0, null));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            int y = current.y;
            int x = current.x;

            // Проверяем, был ли этот узел уже обработан
            if (visited[y][x]) {
                continue;  // Пропускаем этот узел
            }
            visited[y][x] = true;  // Отмечаем узел как посещённый

            if (y == endY && x == endX) {
                return reconstructPath(current);
            }

            for (int[] direction : getNeighbors()) {
                int newY = y + direction[0];
                int newX = x + direction[1];

                if (isInBounds(newY, newX, maze.height(), maze.width()) && maze.grid()[newY][newX] != Cell.WALL) {
                    int newDistance = current.distance + maze.grid()[newY][newX].priority();

                    if (newDistance < distances[newY][newX]
                        && !visited[newY][newX]) {
                        distances[newY][newX] = newDistance;
                        Node neighbor = new Node(newY, newX, newDistance, current);
                        openList.add(neighbor);
                        parents[newY][newX] = current;
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Coordinates> reconstructPath(Node node) {
        List<Coordinates> path = new LinkedList<>();
        Node current = node;
        while (current != null) {
            path.add(0, new Coordinates(current.y, current.x));
            current = current.parent;
        }
        return path;
    }

    private int[][] getNeighbors() {
        return new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    }

    private boolean isInBounds(int y, int x, int height, int width) {
        return y >= 0 && y < height && x >= 0 && x < width;
    }

    private static class Node implements Comparable<Node> {
        int y;
        int x;
        int distance;
        Node parent;

        Node(int y, int x, int distance, Node parent) {
            this.y = y;
            this.x = x;
            this.distance = distance;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
