#!/usr/bin/python3

import sys
import time
import numpy as np
import tkinter as Mazegame
from termcolor import colored
from tkinter import ttk, Canvas, Label

# Mazegame.NoDefaultRoot()


def input_postion(n):
    try:
        src = int(input("Enter the position of Rat: "))
    except:
        src = 0

    if src not in range(0, n*n):
        print("Enter a valid Source Position")
        sys.exit()

    try:
        dest = int(input("Enter the position of Cheese: "))
    except:
        dest = n*n-1

    if dest not in range(0, n*n):
        print("Enter a valid Destination Position")
        sys.exit()

    return src, dest


def rando(n):
    limit = np.random.randint(n*n/4, n*n/2)
    check = list()

    for i in range(limit):
        hold = np.random.randint(n*n-1)
        chk = 0
        for j in range(len(check)):
            if check[j] == hold or hold == 0:
                chk = 1
        if chk == 0:
            check.append(hold)
    return check


def prepare_maze(n, check, src, dest):
    maze = [[0 for i in range(n)] for j in range(n)]
    for i in range(len(check)):
        maze[check[i]//n][check[i] % n] = 1

    maze[src//n][src % n] = 0
    maze[dest//n][dest % n] = 0

    return maze


def display_maze(n, maze, pos):
    print("")
    for i in range(n):
        for j in range(n):
            if pos == i*n+j:
                print(colored("[8]", "blue"), end="")
            elif maze[i][j] == 0:
                print(colored("[0]", "green"), end="")
            elif maze[i][j] == 1:
                print(colored("[1]", "red"), end="")
            elif maze[i][j] == -1:
                print(colored("[3]", "yellow"), end="")
            elif maze[i][j] == 2:
                print(colored("[3]", "cyan"), end="")
        print("")


def make_screen(n):
    if n in range(2, 9):
        size = 300
    elif n in range(9, 43):
        size = 640
    elif n in range(43, 75):
        size = 750
    else:
        print("Invalid Maze size")
        sys.exit()


def path(n, maze, src, dest):
    pos = src
    stack = list()

    while pos != dest:
        r = pos//n
        c = pos % n
        if r in range(n) and c+1 in range(n) and maze[r][c+1] != 1 and maze[r][c+1] != -1 and maze[r][c+1] != 2:
            stack.append(r*n+c)
            maze[r][c] = -1
            pos = pos + 1
        elif r+1 in range(n) and c in range(n) and maze[r+1][c] != 1 and maze[r+1][c] != -1 and maze[r+1][c] != 2:
            stack.append(r*n+c)
            maze[r][c] = -1
            pos = pos + n
        elif r in range(n) and c-1 in range(n) and maze[r][c-1] != 1 and maze[r][c-1] != -1 and maze[r][c-1] != 2:
            stack.append(r*n+c)
            maze[r][c] = -1
            pos = pos - 1
        elif r-1 in range(n) and c in range(n) and maze[r-1][c] != 1 and maze[r-1][c] != -1 and maze[r-1][c] != 2:
            stack.append(r*n+c)
            maze[r][c] = -1
            pos = pos - n
        else:
            maze[pos//n][pos % n] = -1
            if len(stack) == 0:
                msg = "Rat can't find the cheese struck in maze."
                print("Rat can't find the cheese struck in maze.")
                sys.exit()
            else:
                maze[pos//n][pos % n] = 2
                pos = stack.pop()
                while check_pos(pos//n, pos % n, n, maze) != 1:
                    maze[pos//n][pos % n] = 2
                    if len(stack) == 0:
                        display_maze(n, maze, pos)
                        msg = "Rat can't find the cheese struck in maze."
                        print("Rat can't find the cheese struck in maze.")
                        sys.exit()
                    pos = stack.pop()
                    display_maze(n, maze, pos)
                maze[pos//n][pos % n] = 2
        display_maze(n, maze, pos)
    print("Rat Found The Cheese")
    msg = "Rat Found The Cheese"


def check_pos(r, c, n, maze):
    if r in range(n) and c+1 in range(n) and maze[r][c+1] == 0:
        return 1
    elif r+1 in range(n) and c in range(n) and maze[r+1][c] == 0:
        return 1
    elif r in range(n) and c-1 in range(n) and maze[r][c-1] == 0:
        return 1
    elif r-1 in range(n) and c in range(n) and maze[r-1][c] == 0:
        return 1
    return 0


if __name__ == "__main__":
    n = int(input("Enter the dimension of the maze: "))
    src, dest = input_postion(n)
    randno = rando(n)
    maze = prepare_maze(n, randno, src, dest)
    path(n, maze, src, dest)
