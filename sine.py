from math import sin, pi

points = 50
for i in range(points):
    print(round(sin(i / points * pi) * 10000))
