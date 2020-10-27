package com.example.demo.controller.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author muyou
 * @date 2020/8/4 14:31
 * A星寻路算法；
 * G：从起点走到当前格子的成本，也就是已经花费了多少步
 * H：在不考虑障碍的情况下，从当前格子到目标各自的距离，也就是离目标还有多远
 * F：G和H的综合评估，也就是从起点到达当前格子，再从当前格子到达目标格子的总步数
 * F = G + H
 */
public class AStarSearch {
    // 迷宫地图
    public static final int[][] MAZE = {
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 }
    };
    /**
     * A*寻路主逻辑
     * @param start 迷宫起点
     * @param end 迷宫终点
     */
     public static Grid aStarSearch(Grid start, Grid end) {
         ArrayList<Grid> openList = new ArrayList<Grid>();
         ArrayList<Grid> closeList = new ArrayList<Grid>();
         //把起点加入 openList
         openList.add(start);
         //主循环，每一轮检查1个当前方格节点
         while (openList.size() > 0) {
             // 在openList中查找 F值最小的节点，将其作为当前方格节点
             Grid currentGrid = findMinGird(openList);
             // 将当前方格节点从openList中移除
             openList.remove(currentGrid);
             // 当前方格节点进入 closeList
             closeList.add(currentGrid);
             // 找到所有邻近节点
             List<Grid> neighbors = findNeighbors(currentGrid, openList, closeList);
             for (Grid grid : neighbors) {
                if (!openList.contains(grid)) {
                    //邻近节点不在openList 中，标记“父节点”、G、H、F，并放入openList
                    grid.initGrid(currentGrid, end);
                    openList.add(grid);
                }
             }
             //如果终点在openList中，直接返回终点格子
             for (Grid grid : openList){
                 if ((grid.x == end.x) && (grid.y == end.y)) {
                     return grid;
                 }
             }
         }
         //openList用尽，仍然找不到终点，说明终点不可到达，返回空
         return null;
     }
     private static Grid findMinGird(ArrayList<Grid> openList) {
         Grid tempGrid = openList.get(0);
         for (Grid grid : openList) {
             if (grid.f < tempGrid.f) {
                 tempGrid = grid;
             }
         }
         return tempGrid;
     }
     private static ArrayList<Grid> findNeighbors(Grid grid, List<Grid> openList, List<Grid> closeList) {
         ArrayList<Grid> gridList = new ArrayList<Grid>();
            if (isValidGrid(grid.x, grid.y-1, openList, closeList)) {
                gridList.add(new Grid(grid.x, grid.y - 1));
            }
            if (isValidGrid(grid.x, grid.y+1, openList, closeList)) {
                gridList.add(new Grid(grid.x, grid.y + 1));
            }
            if (isValidGrid(grid.x-1, grid.y, openList, closeList)) {
                gridList.add(new Grid(grid.x - 1, grid.y));
            }
            if (isValidGrid(grid.x+1, grid.y, openList, closeList)) {
                gridList.add(new Grid(grid.x + 1, grid.y));
            }
            return gridList;
     }
     private static boolean isValidGrid(int x, int y, List<Grid> openList, List<Grid> closeList) {
         //是否超过边界
         if (x < 0 || x >= MAZE.length || y < 0 || y >= MAZE[0]. length) {
             return false;
         }
         //是否有障碍物
         if(MAZE[x][y] == 1){
             return false;
         }
         //是否已经在openList中
         if(containGrid(openList, x, y)){
             return false;
         }
         //是否已经在closeList 中
         if(containGrid(closeList, x, y)){
             return false;
         }
         return true;
     }
     private static boolean containGrid(List<Grid> grids, int x, int y) {
         for (Grid n : grids) {
             if ((n.x == x) && (n.y == y)) {
                 return true;
             }
         }
         return false;
     }
     static class Grid {
         public int x;
         public int y;
         public int f;
         public int g;
         public int h;
         public Grid parent;
         public Grid(int x, int y) {
             this.x = x;
             this.y = y;
         }
         public void initGrid(Grid parent, Grid end){
            this.parent = parent;
            if(parent != null){
                this.g = parent.g + 1;
            }else {
                this.g = 1;
            }
            this.h = Math.abs(this.x - end.x) + Math. abs(this.y - end.y);
            this.f = this.g + this.h;
         }
     }
     public static void main(String[] args) {
         // 设置起点和终点
         Grid startGrid = new Grid(2, 1);
         Grid endGrid = new Grid(2, 5);
         // 搜索迷宫终点
         Grid resultGrid = aStarSearch(startGrid, endGrid);
         ArrayList<Grid> path = new ArrayList<Grid>();
         while (resultGrid != null){
             path.add(new Grid(resultGrid.x,resultGrid.y));
             resultGrid = resultGrid.parent;
         }
         for (int i = 0; i < path.size(); i++) {
             System.out.println(path.get(i).x + "," + path.get(i).y);
         }
     }
}