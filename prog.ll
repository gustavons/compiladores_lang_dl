;LLVM version 3.8.0 (http://llvm.org/)
;program teste
declare i32 @printf(i8*, ...) nounwind
declare i32 @__isoc99_scanf(i8*, ...) #1
@str_print_int = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@str_print_double = private unnamed_addr constant [7 x i8] c"%.2lf\0A\00", align 1
@str_scan_double = private unnamed_addr constant [4 x i8] c"%lf\00", align 1
@str_scan_int = private unnamed_addr constant [3 x i8] c"%d\00", align 1
declare double @pow(double, double) #1
define i32 @main() nounwind {
%1 = alloca i32
store i32 0, i32* %1
store i32 2, i32* %1
%2 = alloca double
store double 0.0, double* %2
%3 = alloca double
store double 0.0, double* %3
%4 = sitofp i32 3 to double
store double %4, double* %2
%5 = load double, double* %2
%6 = load i32, i32* %1
%7 = sitofp i32 %6 to double
%8 =  call double @pow( double %5, double %7)
store double %8, double* %3
%9 = load double, double* %2
%10 = sitofp i32 0 to double
%11 = fcmp ole double %9, %10
br i1 %11, label %L1, label %L2
L1:
store i32 100, i32* %1
br label %L2
L2:
%12 = load double, double* %3
%13 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %12) ; var %12
ret i32 0
}
