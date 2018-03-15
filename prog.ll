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
%1 = alloca double
store double 0.0, double* %1
%2 = sitofp i32 0 to double
store double %2, double* %1
br label %L1
L1:
%3 = load double, double* %1
%4 = sitofp i32 5 to double
%5 = fcmp ole double %3, %4
br i1 %5, label %L2, label %L3
L2:
%6 = load double, double* %1
%7 = sitofp i32 1 to double
%8 = fadd double %6, %7
store double %8, double* %1
%9 = load double, double* %1
%10 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %9) ; var %9
br label %L1
L3:
ret i32 0
}
