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
%2 = alloca double
store double 0.0, double* %2
%3 = sitofp i32 0 to double
store double %3, double* %2
store i32 2, i32* %1
%4 = sitofp i32 2 to double
%5 =  call double @pow( double %4, double 5.0)
store double %5, double* %2
br label %L1
L1:
%6 = load double, double* %2
%7 = sitofp i32 5 to double
%8 = fcmp ole double %6, %7
br i1 %8, label %L2, label %L3
L2:
%9 = load double, double* %2
%10 = sitofp i32 1 to double
%11 = fadd double %9, %10
store double %11, double* %2
%12 = load double, double* %2
%13 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %12) ; var %12
br label %L1
L3:
ret i32 0
}
