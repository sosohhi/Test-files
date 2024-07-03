# 클래스 학습

# 기존의 add함수
result1 = 0          # 계산기1
def add1(num):
    global result1
    result1 += num   # 결괏값에 입력값 더하기
    return result1   # 결괏값 리턴

result2 = 0          # 계산기2
def add2(num):
    global result2
    result2 +=num
    return result2

print(add1(3))  # ->3 
print(add1(4))  # ->7
print(add2(3))
print(add2(7))


# calculator3.py
class Calculator:
    def __init__(self):     # 생성자(변수를 초기화)와 비슷한 역할
        self.result = 0     # result변수를 0으로 초기화

    def add(self, num):     # add 일반함수
        self.result += num
        return self.result



cal1 = Calculator() # 객체 생성(실제 붕어빵)하여 cal1변수에 대입 (단독 호출 가능)
cal2 = Calculator()

# cal1.add() -> lass Calculator 안에 있는 add함수를 호출
print(cal1.add(3)) 
print(cal1.add(4))
print(cal2.add(3))
print(cal2.add(7))
