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
%3 = alloca double
store double 0.0, double* %3
store double 1.1e-23, double* %3
store i32 0, i32* %1
store i32 10, i32* %2
%4 = load i32, i32* %1
%5 = icmp sle i32 %4, 0
br i1 %5, label %L1, label %L3
L3:
%6 = load i32, i32* %2
%7 = icmp sle i32 %6, 0
br i1 %7, label %L1, label %L2
L1:
store i32 100, i32* %1
br label %L2
L2:
%8 = load i32, i32* %1
%9 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %8) ; var %8
ret i32 0
}
