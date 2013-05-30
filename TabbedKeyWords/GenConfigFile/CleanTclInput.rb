#!/usr/bin/env ruby

require "ftools"

bcstr = ARGV[0]

if bcstr == nil
  bcstr = "FixedVal"
end

ofile =	File.open("input_parse_clean.tcl", "w") 

if File.file?("input_parse.tcl") 
	data = IO.readlines("input_parse.tcl")
	data.each do |line|
		temp =line.lstrip
                if temp[0] != 35
			#           '#' is not first character
			temp = temp.rstrip
                        temp = temp.sub("BC_DEFAULT",bcstr)
			temp = temp + "\n"
			ofile.print temp
		end
	end
end
