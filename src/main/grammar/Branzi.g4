grammar Branzi;

program
    : block EOF ;

block
    : '{' statement* '}';

statement
    : if_statement
    | while_statement
    | declare_statement
    | update_statement
    | expr_el ;

if_statement
    : 'if' '(' expr ')' block ;

while_statement
    : 'while' '(' expr ')' block ;

declare_statement
    : IDENTIFIER ':' type ':=' expr_el;

update_statement
    : lvalue '=' expr_el;

/////////////////7

expr_el
    : expr EL ;

expr
    : '('   Exp = expr   ')'                          # Parens
    | Left = expr Op = (TIMES | DIV)  Right = expr    # MulDiv
    | Left = expr Op = (PLUS  | MINUS) Right = expr   # AddSub
    | array_explicit                                  # ExprArrayExplicit
    | array_access                                    # ExprArrayAccess
    | boolean_expr                                    # ExprBool
    | IDENTIFIER                                      # ExprVar
    | NUMBER                                          # ExprNum
    ;

lvalue
    : array_access  # lvalueArrayAccess
    | IDENTIFIER    # lvalueSimpleVar
    ;

//////////////////////

array_access
    : IDENTIFIER '[' expr ']' ;

array_explicit
    : '[' ']' # emptyArray
    | '[' fst = expr (',' elements+=expr)* ']'  # nonEmptyArray
    ;
///////////////////////

TIMES : '*';
DIV : '/';
PLUS : '+';
MINUS : '-';
//////////////////////

T_INT : 'int';
T_BOOL : 'bool';
T_LIST : 'list';
type
    : '(' type ')'   # parensType
    | (T_INT|T_BOOL) # simpleType
    | T_LIST type    # arrayType
    ;

//////////////////////
B_AND : 'and' ;
B_OR  : 'or'  ;
B_NOT : 'not' ;

boolean_expr
    : 'true'
    | 'false'
    | '(' boolean_expr ')'
    | B_NOT boolean_expr
    | boolean_expr (B_AND|B_OR) boolean_expr
    ;
///////////////////////////

IDENTIFIER : (LETTER (LETTER | DIGIT)*) ;

STRING : '"' (LETTER | DIGIT)+ '"';

NUMBER : (DIGIT)+ ;

WHITESPACE : [ \r\n\t] + -> channel (HIDDEN);

DIGIT : '0'..'9';

LETTER : LOWER | UPPER ;

LOWER : ('a'..'z') ;
UPPER : ('A'..'Z') ;

EL : ';';

