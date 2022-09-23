
from prettytable import PrettyTable
import math
import time

puzzle_size = 16
n_nodes = 0


class Node:
    def __init__(self, state, parent, operator, depth, path_cost):
        self.state = state
        self.parent = parent
        self.children = list()
        self.operator = operator
        self.depth = depth
        self.path_cost = path_cost


    def __lt__(self, other):
        return self.path_cost < other.path_cost

    def is_samePuzzle(self, state):
        """
        This compares two states, in order to verify if they are equal,
        it is also used to verify if the given state is the goal state.
        :param state: state to compare;
        :return: returns True or False whether the given states are equal.
        """
        if self.state == state:
            return True
        else:
            return False

    def move(self):
        """
        This function is used to create the children.
        For that it checks if the moves are possible and creates the
        respective children node.
        """
        new_state = self.state[:]
        blank_index = self.state.index(0)
        global n_nodes

        if blank_index not in [0, 1, 2, 3]:  # UP
            tmp = new_state[blank_index - 4]
            new_state[blank_index - 4] = new_state[blank_index]
            new_state[blank_index] = tmp
            child = Node(new_state, self, "Up", self.depth + 1, self.path_cost)
            self.children.append(child)
            n_nodes += 1

        new_state = self.state[:]
        if blank_index not in [12, 13, 14, 15]:  # DOWN
            tmp = new_state[blank_index + 4]
            new_state[blank_index + 4] = new_state[blank_index]
            new_state[blank_index] = tmp
            child = Node(new_state, self, "Down", self.depth + 1, self.path_cost)
            self.children.append(child)
            n_nodes += 1

        new_state = self.state[:]
        if blank_index not in [0, 4, 8, 12]:  # LEFT
            tmp = new_state[blank_index - 1]
            new_state[blank_index - 1] = new_state[blank_index]
            new_state[blank_index] = tmp
            child = Node(new_state, self, "Left", self.depth + 1, self.path_cost)
            self.children.append(child)
            n_nodes += 1

        new_state = self.state[:]
        if blank_index not in [3, 7, 11, 15]:  # RIGHT
            tmp = new_state[blank_index + 1]
            new_state[blank_index + 1] = new_state[blank_index]
            new_state[blank_index] = tmp
            child = Node(new_state, self, "Right", self.depth + 1, self.path_cost)
            self.children.append(child)
            n_nodes += 1


def print_puzzle(state):
    """
    Prints the matrix of the desired state.
    :param state: state to print.
    """
    table = PrettyTable()
    table.add_row(state[0:4])
    table.add_row(state[4:8])
    table.add_row(state[8:12])
    table.add_row(state[12:16])
    print(table)


def create_menu(option):
    """
    Creates the strategies and heuristics menu.
    :param option: receives different option to create the strategies
    menu or the heuristics.
    """
    if option == 1:
        table = PrettyTable(['Strategies', 'Options'])
        table.add_row(['DFS', 1])
        table.add_row(['BFS', 2])
    print(table)


def has_solution(config):
    """
    Function to check whether any game state is solvable.
    URL: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
    Formula:
       a. If the grid width is odd, then the number of inversions in a solvable
       situation is even.
       b. If the grid width is even, and the blank is on an even row counting
       from the bottom (second-last, fourth-last etc), then the number of
       inversions in a solvable situation is odd.
       c. If the grid width is even, and the blank is on an odd row counting
       from the bottom (last, third-last, fifth-last etc) then the number of
       inversions in a solvable situation is even.
    :param config: configuration array;
    :return: returns true or false based on the formula specified above.
    """
    n_inv = 0
    blank_row = math.ceil((16 - config.index(0)) / 4)
    for i in range(0, puzzle_size):
        for j in range(i + 1, puzzle_size):
            if config[i] > config[j] and config[j] != 0:
                n_inv += 1
    return (blank_row % 2 != 0) == (n_inv % 2 == 0)


def DFS(initialConfig, finalConfig):
    """
    Depth-first search Algorithm.
    :param initialConfig: initial state;
    :param finalConfig: goal state.
    """
    stack = list()
    stack.append(Node(initialConfig, None, "", 0, 0))
    visited = list()
    GoalFound = False
    max_depth = 15

    while stack and not GoalFound:
        node = stack.pop()
        visited.append(node)
        node.move()
        if max_depth > node.depth:
            for child in node.children:
                if child.is_samePuzzle(finalConfig):
                    print("Goal Found!")
                    GoalFound = True
                    path_solution(child)
                if not contains(stack, child) and not contains(visited, child):
                    stack.append(child)



def BFS(initialConfig, finalConfig):
    """
    Breadth-first search Algorithm.
    :param initialConfig: initial state;
    :param finalConfig: goal state.
    """
    queue = list()
    queue.append(Node(initialConfig, None, "", 0, 0))
    visited = list()
    GoalFound = False

    while queue and not GoalFound:
        node = queue.pop(0)
        visited.append(node)
        node.move()
        for child in node.children:
            if child.is_samePuzzle(finalConfig):
                print("Goal Found!")
                GoalFound = True
                path_solution(child)
            if not contains(queue, child) and not contains(visited, child):
                queue.append(child)



def contains(listNode, Node):
    """
    This function checks if a certain state is present in a list of nodes.
    :param listNode: list of nodes;
    :param Node: current node;
    :return: returns True or False, whether the state was found on the list.
    """
    contains = False
    for node in listNode:
        if (node.is_samePuzzle(Node.state)):
            contains = True
    return contains


def path_solution(Node):
    """
    Prints the depth of the node, and goes to each parent node to obtain the
    operator needed to create the path to solution.
    :param Node: Node of the goal state.
    """
    node = Node
    directions = list()
    directions.append(node.operator)
    children = list()
    children.append(node.children)
    depth = node.depth
    while node.parent is not None:
        node = node.parent
        directions.append(node.operator)
    directions.pop()
    print("Path to solution: ", end="")
    print(directions)
    print("Depth: %d" % depth)


def execute(option, initialConfig, finalConfig, heuristic):
    """
    Function used to execute the algorithm and heuristic.
    In the end prints: Number of nodes created, time to finish and memory used.
    :param option: algorithm chosen from the menu;
    :param initialConfig: initial sate;
    :param finalConfig: final state;
    """
    print("Finding Path to Solution...")
    start = time.time()
    global n_nodes

    if option == '1':
        print("Using: DFS")
        DFS(initialConfig, finalConfig)
    elif option == '2':
        print("Using: BFS")
        BFS(initialConfig, finalConfig)


def main():
    initialConfig = list(map(int, input("Initial Configuration: ").split()))
    finalConfig = list(map(int, input("Final Configuration: ").split()))
    if not (has_solution(initialConfig) == has_solution(finalConfig)):
        print("This 15 puzzle has no solution.")
    else:
        print("This 15 puzzle has solution.")
        create_menu(1)
        option = input('Option: ')
        heuristic = 0
        if option not in ['1', '2', '3']:
            create_menu(2)
            heuristic = input('Option: ')
        execute(option, initialConfig, finalConfig, heuristic)

if __name__ == '__main__':
    main()
