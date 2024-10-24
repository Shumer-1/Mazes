package backend.academy.solvers;

import backend.academy.elements.Cell;
import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

public class AStarSolver implements Solver {

    private final int[] directionY = {-1, 1, 0, 0};
    private final int[] directionX = {0, 0, -1, 1};
    private int directionNumber = directionX.length;

    private boolean isValid(int y, int x, Maze maze) {
        return y >= 0 && y < maze.height() && x >= 0 && x < maze.width() && maze.grid()[y][x] != Cell.WALL;
    }

    private int manhattanDistance(int y1, int x1, int y2, int x2) {
        return Math.abs(y1 - y2) + Math.abs(x1 - x2);
    }

    private List<Node> reconstructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        for (Node node = endNode; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Coordinates> solve(Maze maze, Coordinates start, Coordinates end) {
        int startX = start.x();
        int startY = start.y();
        int endY = end.y();
        int endX = end.x();

        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[maze.height()][maze.width()];

        Node startNode = new Node(startY, startX, 0, manhattanDistance(startY, startX, endY, endX), null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.y == endY && current.x == endX) {
                return reconstructPath(current)
                    .stream()
                    .map(Node::toCoordinates)
                    .collect(Collectors.toList());
            }

            closedList[current.y][current.x] = true;

            for (int i = 0; i < directionNumber; i++) {
                int newY = current.y + directionY[i];
                int newX = current.x + directionX[i];

                if (isValid(newY, newX, maze) && !closedList[newY][newX]) {
                    Cell cell = maze.grid()[newY][newX];
                    int newG = cell != Cell.WALL ? current.g + cell.priority() : cell.priority();
                    Node neighbor = new Node(newY, newX, newG, manhattanDistance(newY, newX, endY, endX), current);
                    openList.add(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    @AllArgsConstructor
    private static class Node implements Comparable<Node> {
        int y;
        int x;
        int g;
        int h;
        Node parent;

        public int getF() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.getF(), other.getF());
        }

        public Coordinates toCoordinates() {
            return new Coordinates(y, x);
        }
    }

}

