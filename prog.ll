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
%4 = fmul double 1.1e-23, -1.0
store double %4, double* %3
store i32 0, i32* %1
store i32 10, i32* %2
%5 = load double, double* %3
%6 = sitofp i32 0 to double
%7 = fcmp ole double %5, %6
br i1 %7, label %L1, label %L3
L3:
%8 = load i32, i32* %2
%9 = icmp sle i32 %8, 0
br i1 %9, label %L1, label %L2
L1:
store i32 100, i32* %1
br label %L2
L2:
%10 = load i32, i32* %1
%11 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %10) ; var %10
%12 = load i32, i32* %1
%13 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %12) ; var %12
ret i32 0
}
