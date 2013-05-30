


global env


set tcldir "./"
puts "tcldir: $tcldir"

source input_parse_clean.tcl

set o [open "${tcldir}cuJava.conf" w]

set NumKeys [llength $elements]

puts $o "NumKeys: $NumKeys"

foreach {elem} $elements {
	set basic [lindex $elem 0]
	set para [lindex $elem 1]

	set NameLis [lindex $basic 0]

	if {[llength $NameLis] > 1} {
		set Name [lindex $NameLis 0]
	} else {
		set Name $NameLis
	}
	
	set NumPara [llength $para]	
	puts $o "$Name NumParas: $NumPara"
	foreach {a}  $para {
		set p [lindex $a 0]
		set n [expr [llength $a] - 8]
		
		set name [lindex $a 1]
		set name [lindex $name 0]
		
		set type [lindex $a 2]
		set nt  [llength $type]

		if {$nt == 1} {
			set typest "$type"
		} else {
			set typest "\{ $type \}"
		}
		
		if {$n == 4} {
			set def  [lindex $a  3]
			set label [lindex $a  4]
			set Desc [lindex $a  5]
			set Units [lindex $a  6]
			set Status [lindex $a  7]
			set Mode [lindex $a  8]
			set Range [lindex $a  9]
		} else {
			set def  [lindex $a  4]
			set label [lindex $a 5]
			set Desc [lindex $a  6]
			set Units [lindex $a  7]
			set Status [lindex $a  8]
			set Mode [lindex $a  9]
			set Range [lindex $a  10]
		}

		set nr [llength Range]
		
		if {$nr == 1} {
			set rangest "$Range"
		} else {
			set typest "\{ $Range \}"
		}
		
		puts $o "\t$name $typest $def '$label' '$Desc' '$Units' $Status $Mode $rangest"
			
		# puts $o "    char* $name\_strn;"
		# puts $o "    parsed_status $name\_parsed;" 
		# 
		# if {[llength $type] > 1} {
		# 	puts $o "    $Name\_$name  $name;"
		# 	} elseif {$type == "flag"} {
		# 		puts $o "    int $name;"
		# 		} elseif {$type == "double" || $type == "int"} {
		# 			puts $o "    $type $name;"
		# 
		# 			} else {
		# 				puts $o "    $type $name;"
		# 			}  	    
		# 
		# 			if {$p == "PS" } { 
		# 				puts $o "    void *$Name\_$name\_DATA;" 
		# 			}
		# 		}
		# 	}
		# }
	}
	
# 	} 
# 	puts $o "\n\} $Name\_DATA_struct;\n" 
#     }
#     
#     if {$DataSt == "LL" || $DataSt == "LLA" || 
# 	$DataSt == "LLP" || $DataSt == "LLAP"} {
# 	puts $o "GSList *Process$Name\_Stmnt(char *compLine, GSList *list, Region_data **data);"
#     } elseif {$DataSt == "PST" } { 
# 	puts $o "void Process$Name\_Stmnt(char *compLine, $Name\_DATA_struct **data );"
#     } elseif {$DataSt == "PST_CT" } { 
# 	puts $o "void Process$Name\_Stmnt(char *compLine,$Name\_DATA_struct **data );"
#     }
#     
#     if { $DataSt == "LL" || $DataSt == "LLP" || 
# 	 $DataSt == "PST" || $DataSt == "PST_CT" } {
# 	puts $o "void Free${Name}DataStruct(void *d);"
#     } elseif {$DataSt == "LLAP" || $DataSt == "LLA" } {
# 	puts $o "void Free${Name}DataStruct(char **data);"
#     }
#     
#     puts $o "#endif"
#     close $o
#     
#     #   Create c file
#     set o [open "${tcldir}$Name\_tcl.c" w]
# 
#     puts $o "#include \"$Name\_tcl.h\"\n\n"  
#     puts $o "#include \"parse.h\"\n"  
#     # access fcts
#     
#     # basic type parameter ie Para = XXXX 
#     foreach {a}  $para { 
# 	
# 	
# 	set p [lindex $a 0]
# 	
# 	if {$p == "NP"} { continue } 
# 	
# 	set n [expr [llength $a] - 8]
# 	set fname [lindex $a 1]
# 	set name [lindex $fname 0]
# 	set type [lindex $a 2] 
# 
# 	if {$n == 4} { set type_list $type 
# 	} else { set type_list [lindex $a 3]}
# 	
# 	set ltype [llength $type_list]
# 	
# 	#		puts $o "//// p: $p $type_list $n $ltype"
# 	
# 	if {$ltype > 1} {
# 	    foreach {aname} $fname {
# 		
# 		if {$n == 5} {
# 		    set thead "$type" 
# 		    set head "" 
# 		} else { 
# 		    set thead "$Name\_$name"
# 		    set head "$Name\_$name\_"
# 		}
# 		
# 		set tfirst 1
# 		#				puts $o "$thead Process$Name\_$name\_fct(char *tokenValue)\n\{"				
# 		puts $o "int Process$Name\_$name\_fct(char *tokenValue)\n\{"				
# 		puts $o "\t$thead type;"
# 		
# 		foreach {t} $type_list {
# 		    set tl [llength $t] 
# 		    if {$tl > 1} {
# 			set t0 [lindex $t 0]
# 		    } else {
# 			set t0 $t
# 		    }
# 
# 		    if {$tl == 1} {
# 			if {$tfirst} { 
# 			    puts $o "\tif (tokenValue == NULL || !tjsStrnCmp(tokenValue,\"$t0\")) \{\n\t\ttype = $head$t0;"
# 			    set tfirst 0
# 			} else {
# 			    puts $o "\telse if (!tjsStrnCmp(tokenValue,\"$t0\")) \{\n\t\ttype = $head$t0;"
# 			}
# 			
# 			puts $o "\t\}"
# 		    } else {
# 			set c 0
# 			foreach {tt} $t {
# 			    if {[llength $tt] > 1} { continue }
# 			    if {$c == 0} {
# 				if {$tfirst} { 
# 				    puts $o "\tif (tokenValue == NULL || !tjsStrnCmp(tokenValue,\"$tt\")"
# 				    set tfirst 0
# 				} else {
# 				    puts $o "\telse if (!tjsStrnCmp(tokenValue,\"$tt\")"
# 				}
# 			    } else {
# 				if {$tfirst} { 
# 				    puts $o "\t\t || !tjsStrnCmp(tokenValue,\"$tt\")"
# 				    set tfirst 0
# 				} else {
# 				    puts $o "\t\t || !tjsStrnCmp(tokenValue,\"$tt\")"
# 				}
# 			    }
# 
# 			    incr  c
# 			}
# 			puts $o "\t\t\t\t\t\t)\{\n\t\ttype = $head$t0;"
# 			puts $o "\t\}"
# 		    }
# 		}
# 		#				puts $o "\telse \{ err_msg(1,\"Unknown type for $Name: $name\");\}"
# #		puts $o "\telse type = -1;"
#         puts $o "\telse {err_msg(1,\"Token '%s' Not valid\",tokenValue);}"
# 		puts $o "\treturn type;\n\}\n"				
# 	    }
# 	}
#     }
#     
#     # main Process fct
#     
#     if {$DataSt == "LL" || $DataSt == "LLA" ||
# 	$DataSt == "LLP" || $DataSt == "LLAP"} {
# 	puts $o "\nGSList *Process$Name\_Stmnt(char *compLine, GSList *list, Region_data **data)\n\{"
#     } elseif {$DataSt == "PST" } { 
# 	puts $o "\nvoid Process$Name\_Stmnt(char *compLine, $Name\_DATA_struct **data )\n\{"
#     } elseif {$DataSt == "PST_CT" } { 
# 	puts $o "\nvoid Process$Name\_Stmnt(char *compLine, $Name\_DATA_struct **data )\n\{"
#     }
#     
#     
#     foreach {a}  $para {
# 	set name [lindex $a 1]
# 	set name [lindex $name 0]
# 	set type [lindex $a 2]
# 	if {$type == "GSList*"} {
# 	    puts $o "\tint $name\_list=0, $name\_n=0;"
# 	} 
#     } 
#     puts $o "\tchar *token, *tokenName=NULL, *tokenValue=NULL;"
#     
#     if {$DataSt != "LLA" && $DataSt != "LLAP"} { 
# 	puts $o "\t$Name\_DATA_struct *new;\n"  
#     }
#     
#     
#     
#     if {$DataSt != "LLA" && $DataSt != "LLAP"} {
# 	if {$DataSt == "PST" || $DataSt == "PST_CT"} {
# 	    puts $o "\tif (*data == NULL) \{"
# 	}
# 	
# 	puts $o "\t\tnew = ( $Name\_DATA_struct *) Malloc(sizeof( $Name\_DATA_struct));"
# 	
# 	if {$DataSt == "LL" || $DataSt == "LLP" || $DataSt == "LLA" || $DataSt == "LLAP"} {
# 	    puts $o "\t\tif (data != NULL) \{"
# 	    puts $o "\t\t\t*data = (Region_data *)new;"
# 	    puts $o "\t\t\}"
# 	}
# 	
# 	puts $o "\t\tnew->FreeElementFct = Free${Name}DataStruct;";
# 	
# 	foreach {a} $para {
# 	    set p [lindex $a 0]
# 	    set n [expr [llength $a] - 8]
# 	    set name [lindex $a 1]
# 	    set name [lindex $name 0]
# 	    set type [lindex $a 2]
# 	    if {$n == 4} {set def  [lindex $a  3]
# 	    } else  {set def  [lindex $a  4]}
# 
# 	    if {$type == "GSList*"} {
# 		puts $o "\t\tnew->$name = NULL;"
# 		puts $o "\t\t$name\_list = $name\_n = 0;"
# 	    } elseif {[llength $type] > 1} {
# 		puts $o "\t\tnew->$name = $Name\_$name\_$def;"
# 	    } elseif {$type == "double" || $type == "int"} {
# 		puts $o "\t\tnew->$name = $def;"
# 		
# 	    } else {
# 		puts $o "\t\tnew->$name = $def;"
# 	    }
# 	    
# 	    if {$p == "p" || $p == "P" || $p == "PS"} { 
# 		puts $o "\t\tnew->$name\_parsed = NOT_PARSED;"
# 	    }
# 	    
# 	    if {$p == "PS" } { 
# 		puts $o "\t\tnew->$Name\_$name\_DATA = NULL;" 
# 	    }
# 	    puts $o "\t\tnew->$name\_strn = NULL;"
# 	}
# 	
# 	if {$DataSt == "PST" || $DataSt == "PST_CT"} {
# 	    puts $o "\t\} else new = *data;\n"
# 	}
#     }
#     
#     if {$DataSt != "PST_CT"} {
# 	puts $o "\n\ttoken = strtok(compLine,\" \\t\");"
# 	if {$DataSt != "LLA" && $DataSt != "LLAP" && $DataSt != "PST"} {
# 	    puts $o "\tnew->KeyWord = g_strdup(token);"
# 	}
#     }
#     puts $o "\twhile ((token = strtok(NULL,\" \\t\")) !=  NULL) \{"
#     puts $o "\t\tint FreeToken;"
#     puts $o "\t\tFreeToken = ParameterSplitNoScale(&token,\" = \",&tokenName,&tokenValue);\n"
#     
#     set first 1
#     
#     foreach {a}  $para { 
# 	set p [lindex $a 0]
# 	
# 	if {$p == "NP"} { continue } 
# 	
# 	set n [expr [llength $a] - 8]
# 	set fname [lindex $a 1]
# 	set name [lindex $fname 0]
# 	set type [lindex $a 2] 
# 	if {$n == 4} { set type_list $type 
# 	} else { set type_list [lindex $a 3]}
# 	
# 	foreach {aname} $fname {
# 	    if {$first} { 
# 		puts $o "\t\tif (!g_ascii_strcasecmp(tokenName,\"$aname\")) \{" 
# 		set first 0
# 	    } else { 
# 		puts $o "\t\tif (!g_ascii_strcasecmp(tokenName,\"$aname\")) \{"
# 	    }
# 	    
# 	    if {$DataSt != "LLA" && $DataSt != "LLAP"} {
# 		if {$type == "GSList*"} {
# 		    puts $o "\t\t\tnew->$name\_parsed = PARSED_CONVERTED;"
# 		} else {
# 		    puts $o "\t\t\tnew->$name\_parsed = PARSED;"
# 		}
# 		puts $o "\t\t\tif(tokenValue!= NULL) \{"
# 		puts $o "\t\t\t\tif(new->$name\_strn != NULL) Free(new->$name\_strn);"
# 		puts $o "\t\t\t\tnew->$name\_strn = g_strdup(tokenValue);"
# 		puts $o "\t\t\t\}\n\t\t\telse new->$name\_strn = NULL;\n"
# 	    }
# 	    
# 	    if {$type == "GSList*"} {
# 		
# 		puts $o "\t\t\tif (g_ascii_strcasecmp(tokenValue,\"\[\") != 0)"
# 		puts $o "\t\t\t\{\n\t\t\t\t$name\_list=0;\n\t\t\t\}"
# 		puts $o "\t\t\t\else \{"
# 		puts $o "\t\t\t\t$name\_list = 1; "
# 		
# 		puts $o "\t\t\t\t$name\_n = 0;"
# 		puts $o "\t\t\t\}"
# 		puts $o "\t\t\tcontinue;\n\t\t\}"
# 		puts $o "\t\telse if ($name\_list) \{"
# 		
# 		puts $o "\t\t\tif (g_ascii_strcasecmp(tokenName,\"\]\") == 0) $name\_list = 0; "
# 		puts $o "\t\t\telse \n\t\t\t\{"
# 		set ltype [lindex $a 3]
# 		
# 		if {$ltype == "char*2"} { 
# 		    puts $o "\t\t\t\tchar **data;"
# 		    puts $o "\t\t\t\tdata = (char **) Malloc(2*sizeof(char *));"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tParameterSplitNoScale(&token,\"\",&tokenName,&tokenValue);\n"
# 		    puts $o "\t\t\t\tdata\[0\] = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tParameterSplitNoScale(&token,\"\",&tokenName,&tokenValue);\n"
# 		    puts $o "\t\t\t\tdata\[1\] = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tif (tokenName == NULL)" 
# 		    puts $o "\t\t\t\t\{"
# 		    puts $o "\t\t\t\terr_msg(1,\"Double list malformed.\""
# 		    puts $o "\t\t\t\t	    \"Spaces between values? Correct format: \""
# 		    puts $o "\t\t\t\t	    \"\[ \{str,str\} \{0,0\} \].\");"
# 		    puts $o "\t\t\t\t\}"
# 		} elseif {$ltype == "char*2Eq"} { 
# 		    puts $o "\t\t\t\tEqStruct *data;"
# 		    puts $o "\t\t\t\tdata = (EqStruct *) Malloc(sizeof(EqStruct));"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tParameterSplitNoScale(&token,\"\",&tokenName,&tokenValue);\n"
# 		    puts $o "\t\t\t\tdata->data\[0\] = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tParameterSplitNoScale(&token,\"\",&tokenName,&tokenValue);\n"
# 		    puts $o "\t\t\t\tdata->data\[1\] = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\tdata->EqO = NULL;"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\tif (tokenName == NULL)" 
# 		    puts $o "\t\t\t\t\{"
# 		    puts $o "\t\t\t\terr_msg(1,\"Double list malformed.\""
# 		    puts $o "\t\t\t\t	    \"Spaces between values? Correct format: \""
# 		    puts $o "\t\t\t\t	    \"\[ \{str,str\} \{0,0\} \].\");"
# 		    puts $o "\t\t\t\t\}"
# 		}  elseif {$ltype == "strn_int"} { 
# 		    puts $o "\t\t\t\tstrn_int *data;"
# 		    puts $o "\t\t\t\tdata = (strn_int *) Malloc(sizeof(strn_int));"
# 		    puts $o "\t\t\t\tdata->strn = g_strdup(tokenName);"  
# 		    puts $o "\t\t\t\tdata->in = -1;"
# 		}  elseif {$ltype == "strn_double"} { 
# 		    puts $o "\t\t\t\tstrn_double *data;"
# 		    puts $o "\t\t\t\tdata = (strn_double *) Malloc(sizeof(strn_double));"
# 		    puts $o "\t\t\t\tdata->strn = g_strdup(tokenName);"  
# 		    puts $o "\t\t\t\tdata->d = 0;"
# 		    
# 		    #Look out double2 and int2 do not do parameters correctly - not used yet
# 		    
# 		} elseif {$ltype == "double2"} { 
# 		    puts $o "\t\t\t\tint items;"
# 		    puts $o "\t\t\t\tdouble *data;"
# 		    puts $o "\t\t\t\tdata = (double *) Malloc(2*sizeof(double));"
# 		    puts $o "\t\t\t\titems = sscanf(tokenName,\"\{%lf,%lf\}\",&data\[0\],&data\[1\]);"
# 		    puts $o "\t\t\t\tif (items != 2)" 
# 		    puts $o "\t\t\t\t\{"
# 		    puts $o "\t\t\t\terr_msg(1,\"Double list malformed.\""
# 		    puts $o "\t\t\t\t	    \"Spaces between values? Correct format: \""
# 		    puts $o "\t\t\t\t	    \"\[ \{3.0,4.0\} \{0,0\} \].\");"
# 		    puts $o "\t\t\t\t\}"
# 		} elseif {$ltype == "double3"} { 
# 		    puts $o "\t\t\t\tint items;"
# 		    puts $o "\t\t\t\tdouble *data;"
# 		    puts $o "\t\t\t\tdata = (double *) Malloc(3*sizeof(double));"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\titems = sscanf(token,\"%lf\",&data\[0\]);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\titems = sscanf(token,\"%lf\",&data\[1\]);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    puts $o "\t\t\t\titems = sscanf(token,\"%lf\",&data\[2\]);"
# 		    puts $o "\t\t\t\ttoken = strtok(NULL,\" \\t\");"
# 		    # puts $o "\t\t\t\tif (items != 3)" 
# 		    # puts $o "\t\t\t\t\{"
# 		    # puts $o "\t\t\t\terr_msg(1,\"Triple list malformed.\""
# 		    # puts $o "\t\t\t\t	    \"Spaces between values? Correct format: \""
# 		    # puts $o "\t\t\t\t	    \"\[ \{3.0,4.0,5.0\} \{0,0,0\} \].\");"
# 		    # puts $o "\t\t\t\t\}"
# 		} elseif {$ltype == "int2" } {
# 		    puts $o "\t\t\t\tint items;"
# 		    puts $o "\t\t\t\tint *data;"
# 		    puts $o "\t\t\t\tdata = (int *) Malloc(2*sizeof(int));"
# 		    puts $o "\t\t\t\titems = sscanf(tokenName,\"\{%lf,%lf\}\",&data\[0\],&data\[1\]);"
# 		    puts $o "\t\t\t\tif (items != 2)" 
# 		    puts $o "\t\t\t\t\{"
# 		    puts $o "\t\t\t\terr_msg(1,\"Double list malformed.\""
# 		    puts $o "\t\t\t\t	    \"Spaces between values? Correct format: \""
# 		    puts $o "\t\t\t\t	    \"\[ \{3,4\} \{0,0\} \].\");"
# 		    puts $o "\t\t\t\t\}"
# 		    
# 		    ########### 
# 		    
# 		} elseif {$ltype == "char*"} {   
# 		    puts $o "\t\t\t\tchar *data;"
# 		    puts $o "\t\t\t\tdata = g_strdup(tokenName);"  
# 		} elseif {$ltype == "double"} {
# 		    puts $o "\t\t\t\tstrn_double *data;"
# 		    puts $o "\t\t\t\tdata = (strn_double *) Malloc(sizeof(strn_double));"
# 		    puts $o "\t\t\t\tdata->strn = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\tif(tokenName !=  NULL) \{"
# 		    puts $o "\t\t\t\t\tif (!ConvertDoublePara(tokenName,&(data->d)))new->$name\_parsed = PARSED;"
# 		    puts $o "\t\t\t\t\} else err_msg(1,\"Missing tokenvalue for: %s\\n\",tokenName);"
# 		} elseif {$ltype == "int"} {
# 		    puts $o "\t\t\t\tstrn_int *data;"
# 		    puts $o "\t\t\t\tdata = (strn_int *) Malloc(sizeof(strn_int));"
# 		    puts $o "\t\t\t\tdata->strn = g_strdup(tokenName);"
# 		    puts $o "\t\t\t\tif(tokenName !=  NULL) \{"
# 		    puts $o "\t\t\t\t\tif (!ConvertIntPara(tokenName,&(data->in)))new->$name\_parsed = PARSED;"
# 		    puts $o "\t\t\t\t\} else err_msg(1,\"Missing tokenvalue for: %s\\n\",tokenName);"
# 		}
# 		
# 		if {$DataSt == "LL"} {
# 		    puts $o "\t\t\t\tnew->$name = g_slist_append(new->$name,data);" 
# 		} elseif {$DataSt == "LLP"} {
# 		    puts $o "\t\t\t\tnew->$name = g_slist_append(new->$name,data);"
# 		} elseif {$DataSt == "LLAP"} {
# 		    puts $o "\t\t\t\tlist = g_slist_append(list,data);"
# 		} else {
# 		    puts $o "\t\t\t\tnew->$name = g_slist_append(new->$name,data);"
# 		}
# 		
# 		puts $o "\t\t\t\t$name\_n++;"
# 		puts $o "\t\t\t\}"
# 	    } elseif {[llength $type] > 1 || $n == 5} {
# 		if {$p == "PS"} {
# 		    if {$n == 5} { set head "" 
# 		    } else { set head "$Name\_$name\_"}
# 		    set tfirst 1
# 		    
# 		    foreach {t} $type_list {
# 			set tl [llength $t] 
# 			if {$tl > 1} {
# 			    set t0 [lindex $t 0]
# 			} else {
# 			    set t0 $t
# 			}
# 			
# 			if {$tl == 1} {
# 			    if {$tfirst} { 
# 				puts $o "\t\t\tif (!tjsStrnCmp(tokenValue,\"$t0\")) \{\n\t\t\t\tnew->$name = $head$t0;"
# 				set tfirst 0
# 			    } else {
# 				puts $o "\t\t\telse if (!tjsStrnCmp(tokenValue,\"$t0\")) \{\n\t\t\t\tnew->$name = $head$t0;"
# 			    }
# 			} else {
# 			    set c 0
# 			    foreach {tt} $t {
# 				if {[llength $tt] > 1} { continue }	
# 				if {$c == 0} {
# 				    if {$tfirst} { 
# 					puts $o "\t\t\tif (!tjsStrnCmp(tokenValue,\"$tt\")"
# 					set tfirst 0
# 				    } else {
# 					puts $o "\t\t\telse if (!tjsStrnCmp(tokenValue,\"$tt\")"
# 				    }
# 				} else {
# 				    if {$tfirst} { 
# 					puts $o "\t\t\t\t || !tjsStrnCmp(tokenValue,\"$tt\")"
# 					set tfirst 0
# 				    } else {
# 					puts $o "\t\t\t\t || !tjsStrnCmp(tokenValue,\"$tt\")"
# 				    }
# 				}
# 				
# 				incr  c
# 			    }
# 			    puts $o "\t\t\t\t\t\t\t\t)\{\n\t\t\t\tnew->type = $head$t0;"
# 			}
# 			
# 			puts $o "\t\t\t\tProcess$t0\_$Name\_Stmnt\(compLine,\($t0\_$Name\_DATA_struct **\) &\(new->$Name\_$name\_DATA\)\);"
# 			puts $o "\t\t\t\tbreak;\n\t\t\t\}"
# 		    }
# 		    puts $o "\t\t\telse err_msg(1,\"Unknown parameter value '%s' for parameter '%s'\\n\", tokenValue,tokenName);\n"
# 		} else {
# 		    puts $o "\t\t\tnew->$name = Process$Name\_$name\_fct(tokenValue);"
# 		}
# 		#					puts $o "\t\t\tnew->$name\_strn = g_strdup(tokenValue);"
# 	    } elseif {$type == "flag"} {
# 		
# 		puts $o "\t\t\tif(tokenValue !=  NULL)\{"
# 		#puts $o "\t\t\tif (new->$name\_strn != NULL) Free(new->$name\_strn);"
# 		#puts $o "\t\t\t\tnew->$name\_strn = g_strdup(tokenValue);" 
# 		puts $o "\t\t\t\tif(ConvertIntPara(new->$name\_strn,&(new->$name)))" 
# 		puts $o "\t\t\t\tnew->$name\_parsed = PARSED_CONVERTED;"
# 		puts $o "\t\t\t\}"
# 		puts $o "\t\t\telse new->$name = 1;"  
# 	    } else {
# 		if {$type == "char*"} { 
# 		    puts $o "\t\t\tif (new->$name != NULL) Free(new->$name);"
# 		    puts $o "\t\t\tnew->$name = g_strdup(tokenValue);"  
# 		} elseif {$type == "double"} { 
# 		    puts $o "\t\t\tif(tokenValue !=  NULL)\{"
# 		    puts $o "\t\t\t\tif(ConvertDoublePara(new->$name\_strn,&(new->$name)))" 
# 		    puts $o "\t\t\t\tnew->$name\_parsed = PARSED_CONVERTED;"
# 		    puts $o "\t\t\t\}"
# 		    puts $o "\t\t\telse err_msg(1,\"Missing tokenvalue for: %s\\n\",tokenName);"
# 		} elseif {$type == "int"} {
# 		    puts $o "\t\t\tif(tokenValue !=  NULL)\{"
# 		    puts $o "\t\t\t\tif(ConvertIntPara(new->$name\_strn,&(new->$name)))" 
# 		    puts $o "\t\t\t\tnew->$name\_parsed = PARSED_CONVERTED;"
# 		    puts $o "\t\t\t\}"
# 		    puts $o "\t\t\t\telse err_msg(1,\"Missing tokenvalue for: %s\\n\",tokenName);"
# 		}
# 	    } 
# 	    puts $o "\t\t\tif (FreeToken) Free(token);"
# 	    
# 	    puts $o "\t\t\tcontinue;\n\t\t\}"
# 	}
#     }
#     
#     puts $o "\t\}"
#     
# 
#     puts $o "\tFree(tokenName);"
#     puts $o "\tFree(tokenValue);\n"
#     
#     if {$DataSt == "LL"} {
# 	puts $o "\tlist = g_slist_append (list, new);"  
# 	puts $o "\treturn(list);\n\}"
#     } elseif {$DataSt == "LLP"} {
# 	puts $o "\tlist = g_slist_prepend (list, new);"  
# 	puts $o "\treturn(list);\n\}"
#     } elseif {$DataSt == "LLA"} {
# 	puts $o "\treturn(list);\n\}"
#     } elseif {$DataSt == "LLAP"} {
# 	puts $o "\treturn(list);\n\}"
#     } elseif {$DataSt == "PST" || $DataSt == "PST_CT"} { 
# 	puts $o "\t*data = new;"
# 	puts $o "\}"
#     } else {
# 	puts $o "\tsomething to break the C compiler as unknown datast"
#     }
#     
#     if { $DataSt == "LL" || $DataSt == "LLP" ||   
# 	 $DataSt == "PST" || $DataSt == "PST_CT" } {
# 	
# 	puts $o "\nvoid Free${Name}DataStruct(void *vd)\n\{"
# 	puts $o "\n\t$Name\_DATA_struct *d = ($Name\_DATA_struct *)vd;\n"
# 	puts $o "\n\tif (vd == NULL) return;\n"
# 	
# 	foreach {a}  $para {
# 	    set p [lindex $a 0]
# 	    set name [lindex $a 1]
# 	    set name [lindex $name 0]
# 	    set type [lindex $a 2]
# 	    set n [expr [llength $a] - 8]
# 	    
# 	    if { $p == "NP" } { continue }
# 	    
# 	    if {$n == 4} { set type_list $type 
# 	    } else { set type_list [lindex $a 3]}
# 
# 	    puts $o "\tif (d->$name\_strn != NULL) Free (d->$name\_strn);"
# 	    
# 	    if { $type == "char*" } {
# 		puts $o "\tif (d->$name != NULL) Free(d->$name);"
# 	    } elseif {$type == "EqStruct*"} {
# 		puts $o "\tif (d->$name != NULL) FreeEqStruct(d->$name);"
# 	    } elseif {$type == "GSList*"} {
# 		set ltype [lindex $a 3]
# 		puts $o "\tif (d->$name != NULL) \{"
# 		
# 		if {$ltype == "strn_int"} { 
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) FreeStrInt, NULL);"
# 		} elseif {$ltype == "strn_double"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) FreeStrDouble, NULL);"
# 		} elseif {$ltype == "char*"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) Free, NULL);"
# 		} elseif {$ltype == "double"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) FreeStrDouble, NULL);"
# 		} elseif {$ltype == "int"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) FreeStrInt, NULL);"
# 		} elseif {$ltype == "char*2"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) Free2ElemArray, NULL);"
# 		} elseif {$ltype == "char*2Eq"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) FreeEqStruct, NULL);"
# 		} elseif {$ltype == "double2" || $ltype == "double3" || $ltype == "int2"} {
# 		    puts $o "\t\tg_slist_foreach (d->$name, (GFunc) Free, NULL);"
# 		} else {
# 		    puts "ltype '$ltype' not known for glist free"
# 		    exit
# 		}
# 		
# 		puts $o "\t\tg_slist_free (d->$name);"
# 		puts $o "\t\}"
# 	    } elseif { $type == "DnsReMatrix*" } {
# 		#puts $o "\tif (d->$name != NULL) \{"
# 		#puts $o "\t\tDnsReDestroyMatrix (d->$name);"
# 		#puts $o "\t\tFree(d->$name);"
# 		#puts $o "\t\}"
# 	    } elseif {$type == "double" || $type == "int"} {
# 
# 	    } elseif {[llength $type] > 1} {
# 		if {$n == 5} { set head "" 
# 		} else { set head "$Name\_$name\_"}
# 		if {$p == "PS"} {
# 		    foreach {t} $type_list {
# 			set tt [lindex $t 0]
# 			if {[llength $tt] > 1} { continue }
# 			puts $o "\tif (d->$name ==  $head$tt) Free$tt\_${Name}DataStruct( \(d->$Name\_$name\_DATA\)\);"
# 		    }
# 		}
# 	    }
# 	    
# 	}
# 
# 	if {$DataSt != "PST_CT" &&  $DataSt != "PST"} {
# 	    puts $o "\tFree(d->KeyWord);"
# 	}
# 	puts $o "\tFree(d);"
# 	puts $o "\}"
#     } elseif {$DataSt == "LLAP" || $DataSt == "LLA"} {
# 	puts $o "\nvoid Free${Name}DataStruct(char **data)\n\{"
# 	puts $o "\tFree (data\[0\]);"
# 	puts $o "\tFree (data\[1\]);"
# 	puts $o "\tFree (data);"
# 	puts $o "\}"
#     }
#     
#     close $o
# }
# 
# puts $h "\#endif"
# close $h

exit
