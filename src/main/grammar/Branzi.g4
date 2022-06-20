grammar Branzi;

program
    : unit* EOF ;

unit
    : function_declaration # funcDecl
    | block # blockDeclaration
    ;

block
    : '{' statement* '}';
block_brk_cnt
    : '{' statement_brk_cnt* '}';

statement
    : if_statement
    | while_statement
    | declare_statement
    | update_statement
    | return_stmt
    | expr_el ;

statement_brk_cnt
    : break_statement
    | continue_statement
    | statement
    ;

if_statement
    : 'if' '(' expr ')' block ;

while_statement
    : 'while' '(' expr ')' block_brk_cnt ;

declare_statement
    : IDENTIFIER ':' type ':=' expr_el;

update_statement
    : lvalue '=' expr_el;

break_statement
    : 'break' EL ;

continue_statement
    : 'continue' EL ;

return_stmt
    : 'return' ';'       # emptyReturn
    | 'return' expr ';'  # happyReturn
    ;

/////////////////7

expr_el
    : expr EL ;

expr
    : funcall                                         # ExprFuncall
    | '('   Exp = expr   ')'                          # Parens
    | Left = expr Op = (TIMES | DIV)  Right = expr    # MulDiv
    | Left = expr Op = (PLUS  | MINUS) Right = expr   # AddSub
    | Left = expr Op = ('=='|'!='|'<'|'<='|'>'|'>=') Right = expr  # EqTest
    | Left = expr Op = (B_AND|B_OR|B_XOR) Right = expr  # boolBinop
    | array_explicit                                  # ExprArrayExplicit
    | array_access                                    # ExprArrayAccess
    | B_TRUE                                          # boolTrue
    | B_FALSE                                         # boolFalse
    | IDENTIFIER                                      # ExprVar
    | NUMBER                                          # ExprNum
    ;

lvalue
    : array_access  # lvalueArrayAccess
    | IDENTIFIER    # lvalueSimpleVar
    ;

funcall
    : IDENTIFIER '(' ')'  # FuncallNoArgs
    | IDENTIFIER '(' arg1 = expr (',' otherArgs+=expr)* ')' # FuncallWithArgs
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
    : 'function' Name = IDENTIFIER ':' func_type  '('  ')'
             block            # funcDeclEmptyArgs
    | 'function' Name = IDENTIFIER ':' func_type
             '(' arg1 = IDENTIFIER (',' otherArgs+=IDENTIFIER)* ')'
             block            # funcDeclNonEmptyArgs
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
T_STR : 'string'; // TODO
T_ANY  : 'any';
T_VOID  : 'void';

type
    : simple_type
    | func_type
    ;

simple_type
    : '(' simple_type ')'   # parensType
    | basetype=(T_INT|T_BOOL|T_ANY|T_VOID|T_STR)  # baseType
    | T_LIST simple_type    # arrayType
    ;

func_type:
    | '('? arg1 = simple_type ('->' otherArgs+=simple_type )* ')'?
    ;

//////////////////////
B_TRUE : 'true';
B_FALSE : 'false';
B_AND : 'and' ;
B_OR  : 'or'  ;
B_NOT : 'not' ;
B_XOR : 'xor' ;

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

