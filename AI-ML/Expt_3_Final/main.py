import sys
# used to run main() when this file is run as a script

#  calculating the total cost of the path


def F(node):
    return node.G + node.H

#  Data structure for maze


class Queue:
    def __init__(self):
        self.contents = []  # list of nodes

    def push(self, node):
        self.contents.append(node)  # add node to the list
        print("pushed node: ", node.x, node.y)

    def contains(self, x, y):
        for node in self.contents:
            if node.x == x and node.y == y:
                print("contains node: ", node.x, node.y)
                return True
        return False

    # remove the node with the lowest f value
    def pop(self):
        min = None
        min_index = -1
        # find the node with the lowest f value
        for i in range(len(self.contents)):
            if min == None or F(self.contents[i]) < F(min):
                min = self.contents[i]
                min_index = i
        if min == None:  # if the list is empty
            return None
        else:
            self.contents.pop(min_index)  # remove the node from the list
            return min

    # check if the list is empty
    def is_empty(self):
        if len(self.contents) == 0:
            return True
        else:
            return False

# Reads input and contains a function to determine whether a particular cell is blocked


class Maze:
    def __init__(self, filename):
        # read file + initialize matrix
        self.matrix = []
        self.dim_x = 0
        self.dim_y = 0
        self.readfile(filename)

    # print the maze
    def print(self):
        print("Dims: ", self.dim_y, self.dim_x)
        for row in self.matrix:
            print(row)

    # # self added
    # # method to print path at each step
    # def print_path(self, node):
    #     path = []
    #     while node.parent != None:
    #         path.append(node)
    #         node = node.parent
    #     path.append(node)
    #     path.reverse()
    #     for node in path:
    #         print("(", node.x, ",", node.y, ")")
    #     print("")
    #     return path

    #  read the maze from the file
    def readfile(self, name):
        F = open(name, "r")
        dim_line = F.readline()
        dims = [int(v) for v in dim_line.split()]
        self.dim_y, self.dim_x = dims[0], dims[1]
        lines = F.readlines()
        for line in lines:
            self.matrix.append([int(val) for val in line.split()])

    #  check if the cell is blocked
    def blocked(self, x, y):
        return (self.matrix[y][x] == 0)

# check if rat is in range of the maze


def in_range(coordinates, maze):
    if coordinates[0] < 0 or coordinates[0] >= maze.dim_x or coordinates[1] < 0 or coordinates[1] >= maze.dim_y:
        return False
    return True

# calculate the distance between two nodes


def manhattan_distance(node, x, y):
    a = abs(node.x - x)
    b = abs(node.y - y)
    return 0

# function to give the coordinates of the neighbours of a node


def cell_number(node, maze):
    x = node.x
    y = node.y
    return (y * maze.dim_x) + x + 1


class Node:
    def __init__(self, x, y, maze):
        self.x = x
        self.y = y
        # manhattan distance from source
        self.G = manhattan_distance(self, 0, 0)
        # manhattan distance to goal
        self.H = manhattan_distance(self, maze.dim_x-1, maze.dim_y-1)
        self.children = []
        self.parent = None

# has 3 member functions- solve, solution, and trace_path


class Solver:
    # initialize the solver
    def __init__(self, maze):
        self.maze = maze

    # trace the path from the goal node to the start node
    def trace_path(self, node):
        path = []
        cu = node
        while cu is not None:
            path = [cu] + path
            cu = cu.parent
        return ' '.join(str(cell_number(i, self.maze)) for i in path)

    # print the solution
    def solution(self):
        r = self.solve()
        if r is not None:
            print("Solution found")
            return self.trace_path(r)
        else:
            print("No solution found")
            return "No Solution"

    # print the path for all the nodes
    def print_path(self, node):
        path = []
        while node.parent != None:
            path.append(node)
            node = node.parent
        path.append(node)
        path.reverse()
        for node in path:
            print("(", node.x, ",", node.y, ")")
        print("")
        return path

    # conducts the A* search on the maze and returns a Node object if a solution exists or None if there is no solution
    def solve(self):
        node_cu = None
        start = Node(0, 0, self.maze)
        q = Queue()
        explored = []
        q.push(start)

        # checks if the queue is empty
        while not q.is_empty():
            node_cu = q.pop()
            if node_cu.x == (self.maze.dim_x - 1) and node_cu.y == (self.maze.dim_y-1):
                # sol found
                break
            # generate neighbor coordinates (left, down, right, up)
            possible_children = [(node_cu.x - 1, node_cu.y),
                                 (node_cu.x, node_cu.y - 1),
                                 (node_cu.x + 1, node_cu.y),
                                 (node_cu.x, node_cu.y + 1)]
            # insert valid children into q
            for pair in possible_children:
                # if valid move (not already visited + not explored + not blocked)_
                if in_range(pair, self.maze) and not q.contains(*pair) and not self.maze.blocked(*pair) and pair not in explored:
                    child = Node(*pair, self.maze)
                    child.parent = node_cu
                    q.push(child)
                    node_cu.children.append(child)
                    # ignore check to see if state exists in q or explored w/lower g
                    # since manhattan distance from source will always be the same for same coordinates
            explored.append((node_cu.x, node_cu.y))
        if node_cu is None or node_cu.x != (self.maze.dim_x-1) or node_cu.y != (self.maze.dim_y-1):
            return None
        else:
            return node_cu


def main():
    'To run: python main.py ip_RatinMaze.txt op_RatinMaze.txt'
    if (len(sys.argv) != 3):
        print('Usage: main.py <input file name> <output file name>\n\tex: main.py input.txt output.txt')
        return
    else:
        input_name, output_name = sys.argv[1], sys.argv[2]
    print('Conducting A* search on input file (' + input_name + ')')
    m = Maze(input_name)  # maze class which reads input file into maze matrix
    m.print()
    s = Solver(m)  # Solver class which conducts A* search on maze m
    sol = s.solution()  # returns solution (or lack thereof) as a string
    outfile = open(output_name, 'w')
    outfile.write(sol)


if __name__ == "__main__":
    main()

# input.txt
# 4 4
# 1 1 1 1
# 1 1 1 1
# 1 1 1 1
# 1 1 1 1

# output.txt
# 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
