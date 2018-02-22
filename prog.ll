;LLVM version 3.8.0 (http://llvm.org/)
;program teste
declare i32 @printf(i8*, ...) nounwind
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
define i32 @main() nounwind {
%1 = alloca i32
store i32 0, i32* %1
%2 = alloca i32
store i32 0, i32* %2
store i32 0, i32* %1
store i32 10, i32* %2
%3 = load i32, i32* %1
%4 = icmp sle i32 %3, 0
br i1 %4, label %L1, label %L3
L3:
%5 = load i32, i32* %2
%6 = icmp sle i32 %5, 0
br i1 %6, label %L1, label %L2
L1:
store i32 100, i32* %1
br label %L2
L2:
%7 = load i32, i32* %1
%8 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %7) ; var %7
ret i32 0
}
