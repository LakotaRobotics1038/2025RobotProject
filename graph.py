import matplotlib.pyplot as plt
from math import cos, pi, floor, acos, sqrt

base_x = []
for i in range(-1000, 1000):
    base_x.append(i / 1000)
base_y = []
for i in range(-1000, 1000):
    base_y.append(acos(i / 1000))

approx_y = []
for i in range(-1000, 1000):
    x = i / 1000
    approx_y.append(
        (-0.69813170079773212 * x * x - 0.87266462599716477) * x + 1.5707963267948966
    )
new_y = []
for i in range(-1000, 1000):
    x = i / 1000
    new_y.append((-0.798325 * x * x - 0.686357) * x + 1.570796)


plt.plot(base_x, base_y)
# plt.plot(base_x, approx_y)
plt.plot(base_x, new_y)

plt.xlabel("x - axis")
plt.ylabel("y - axis")

plt.title("Cosine Approximation")

plt.show()
