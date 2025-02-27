# Консольное приложение "Лабиринты", написанное во время обучения в Академии Бэкенда
Для того, чтобы использовать этот код, необходимо запустить
метод main класса Main. Далее требуется следовать подсказкам
в консоли, вводить туда нужные значения, которые определят каким
генератором будет создаваться лабиринт и какой алгоритм будет искать
в нем пути.

На данный момент поддерживается два алгоритма генерации лабиринта:
алгоритм, основанный на алгоритме Прима, и алгоритм, основанный на 
алгоритм DFS. Введите соответствующее слово (на экране будут указаны
варианты). В случае ввода некорректного значения вам потребуется ввести 
слово заново.

На данный момент поддерживается два алгоритма поиска пути в лабиринте:
алгоритм Астар (А*) и алгоритм Дейкстры. Введите соответствующее слово 
(на экране будут указаны варианты). В случае ввода некорректного значения 
вам потребуется ввести слово заново.

После выбора алгоритмов вам потребуется ввести параметры лабиринта: ширину и
высоту. В консоли будут указаны значения, которые применятся в случае ошибочного
ввода следующим образом: 
1) Значения не соответствуют формату (целые неотрицательные числа не превышающие 
2^31) -> значения по умолчанию.
2) Значения больше, чем установленный максимум -> максимальные значения, заданные
в консоли.
3) Значения меньше, чем установленный минимум -> минимальные значения, заданные
в консоли.

Затем в консоли отрисуется лабиринт. Значения символов в нем:
1) 🌳 - стена (непроходимая клетка).
2) 🟫 - проход (обычная клетка).
3) 🪙 - монета (клетка с положительным приоритетом).
4) 🌊 - болото (клетка с отрицательным приоритетом).

Далее вам потребуется ввести четыре значения, где первые два - это
координаты первой клетки (старт), вторые два значения - координаты
второй клетки (цель). Если вы введете некорректное значение (неверный
формат, значения меньше 0 или больше размеров лабиринта), вам потребуется
ввести значения заново. При этом если вы выберете непроходимый элемент (такой 
сценарий относится к корректным, то есть не требует перевыбора клеток), то
соответствующая подсказка укажет на это.

После вашего выбора появится лабиринт с отрисованным путем из точки старта в
точку финиша. Путь отображается специальным символом (🟥).

Затем вы должны будете ввести 'yes', если хотите рассмотреть другие пути на данном
лабиринте, и 'no', если хотите завершить работу. В случае ввода некорректных
значений, вам потребуется повторить ввод.

Ввод 'no' завершит работу, ввод 'yes' перекинет вас на этап выбора точек пути.
