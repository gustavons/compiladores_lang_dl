;LLVM version 3.8.0 (http://llvm.org/)
;program teste
declare i32 @printf(i8*, ...) nounwind
declare i32 @__isoc99_scanf(i8*, ...) #1
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
@str_scan_double = private unnamed_addr constant [4 x i8] c"%lf\00", align 1
@str_scan_int = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define i32 @main() nounwind {
%1 = alloca i32
store i32 0, i32* %1
%2 = alloca i32
store i32 0, i32* %2
%3 = alloca double
store double 0.0, double* %3
%4 = alloca i32
store i32 0, i32* %4
%5 = alloca i1
store i1 0, i1* %5
%6 = fmul double 1.1e-23, -1.0
store double %6, double* %3
store i32 0, i32* %1
store i32 10, i32* %2
%7 = load double, double* %3
%8 = sitofp i32 0 to double
%9 = fcmp ole double %7, %8
br i1 %9, label %L1, label %L3
L3:
%10 = load i32, i32* %2
%11 = icmp sle i32 %10, 0
br i1 %11, label %L1, label %L2
L1:
store i32 100, i32* %1
br label %L2
L2:
%12 = load i32, i32* %1
%13 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @str_scan_int, i32 0, i32 0), i32* %1);
%14 = load i32, i32* %1
%15 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %14) ; var %14
%16 = load double, double* %3
%17 = load i32, i32* %2
%18 = sitofp i32 %17 to double
%19 = fcmp ole double %16, %18
br i1 %19, label %L4, label %L5
L4:
%20 = load i32, i32* %2
%21 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %20) ; var %20
br label %L5
L5:
%22 = load double, double* %3
%23 = sitofp i32 2 to double
%24 = fadd double %22, %23
%25 = sitofp i32 2 to double
%26 =  double %24, %25
%27 = sitofp i32 5 to double
%28 =  double %26, %27
store double %28, double* %3
%29 = load i32, i32* %4
%30 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([4 x i8], [4 x i8]* @str_print_int, i32 0, i32 0), i32 %29) ; var %29
ret i32 0
}
