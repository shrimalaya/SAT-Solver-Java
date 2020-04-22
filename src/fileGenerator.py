import random

lines = []
k = 4


""" CNFk(m, n) denotes a k-CNF sentence with m clauses and n
symbols, where the clauses are chosen uniformly, independently, and without replacement
from among all clauses with k different literals, which are positive or negative at random. (A
symbol may not appear twice in a clause, nor may a clause appear twice in a sentence.)
"""


def randomGenerator(m, n):
    global lines
    lines.append(f'p cnf {m} {n}')

    for j in range(m):
        chosenSymbols = []
        line = ""
        for i in range(k):
            val = random.randint(1, n)
            while chosenSymbols is not None and val in chosenSymbols:
                val = random.randint(1, n)
            chosenSymbols.append(val)
            sign = random.choice([-1, 1])
            line += str(val*sign) + " "
        line += "0"
        if line in lines:
            j -= 1
        else:
            lines.append(line)


'''___________________________________________CREATE FILE______________________________________________'''


def fileGenerator(name):
    f = open(name, "w")
    for line in lines:
        f.write(line + "\n")
    f.close()


def run():
    global k
    k = int(input("Give a value for k: "))
    m = int(input("Give a value for m (# clauses): "))
    n = int(input("Give the value for n (# variables): "))

    while k > n:
        print("k cannot be greater than n.\nPlease re-enter the values.")
        k = int(input("k: "))
        m = int(input("m (# clauses): "))
        n = int(input("n (# variables): "))

    randomGenerator(m, n)
    fileGenerator(input("What do you want to name the (.txt) file? ") + ".txt")


'''_______________________________________RUN FROM COMMAND LINE________________________________________'''

if __name__ == '__main__':
    run()
