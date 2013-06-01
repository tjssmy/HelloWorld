


global env


set tcldir "./"
puts "tcldir: $tcldir"

source input_parse_clean.tcl

set o [open "${tcldir}cuJava.conf" w]

set NumKeys [llength $elements]

puts $o "NumKeys: $NumKeys"

set k 0
foreach {elem} $elements {
	incr k
	set basic [lindex $elem 0]
	set para [lindex $elem 1]

	set NameLis [lindex $basic 0]

	if {[llength $NameLis] > 1} {
		set Name [lindex $NameLis 0]
	} else {
		set Name $NameLis
	}
	
	set NumPara [llength $para]	
	puts $o "$k $Name NumParas: $NumPara"
	
	set pn 0
	foreach {a}  $para {
		incr pn
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
		
		puts $o "$pn $name $typest $def '$label' '$Desc' '$Units' $Status $Mode $rangest"
	
	}
}

exit
