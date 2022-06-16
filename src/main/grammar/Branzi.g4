grammar Branzi;

program
    : unit* EOF ;

unit
    : function_declaration # funcDecl
    | block # blockDeclaration
    ;

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

function_declaration
    : 'function' Name = IDENTIFIER ':' type  '('  ')'  block # funcDeclEmptyArgs
    | 'function' Name = IDENTIFIER ':' type
                  '(' arg1 = IDENTIFIER (',' otherArgs+=IDENTIFIER)* ')'
                  block # funcDeclNonEmptyArgs
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
T_ANY  : 'any';

type
    : simple_type
    | func_type
    ;

simple_type
    : '(' simple_type ')'   # parensType
    | basetype=(T_INT|T_BOOL|T_ANY)  # baseType
    | T_LIST simple_type    # arrayType
    ;

func_type:
    | '('? arg1 = simple_type ('->' otherArgs+=simple_type )+ ')'?
    ;

//////////////////////
B_TRUE : 'true';
B_FALSE : 'false';
B_AND : 'and' ;
B_OR  : 'or'  ;
B_NOT : 'not' ;
B_XOR : 'xor' ;

boolean_expr
    : B_TRUE  # boolTrue
    | B_FALSE # boolFalse
    | '(' boolean_expr ')' # boolParens
    | B_NOT boolean_expr   # boolNot
    | Left = boolean_expr Op = (B_AND|B_OR|B_XOR) Right = boolean_expr # boolBinop
    // | IDENTIFIER # boolIdentifier
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

