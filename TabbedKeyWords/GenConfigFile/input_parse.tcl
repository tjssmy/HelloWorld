
#      list of elements (both physical elements and other parsed objects/lines)
#
# each element has two basic parts {info} {parameters}
# info = { ElementName Datatype } 
# Datatypes are LL or  LLP  - linked list of structures
#               LLAP or LLA - adds to one single parameter list of structures
#               PST         - a pointer to a single stucture            
#               PST_CT      - a pointer to a single stucture
#
# List of Parameters of each of the form:
#     { P|NP Name ptype defaultvalue "LabelString" "DescriptionString" "UnitString" OPTIONAL/REQUIRED/NOTFUNCTIONAL None/Opti/Spice DocPos {para range}}
#         P  - indicates parsed and converted (if possible) para 
#         NP - indicates not a parsed para, 
#         PS - indicates a parsed parameter that calls a PST_CT function and    
#              returns a datastructure pointed to by a void pointer
#              (see Model statement for example)
#
#         Name is the parameter name or a list of names (synonyms) for that parameter
#               ie Temperature
#               ie {Temp T Temperature T0} 
#  
#         ptype is a simple double, char*, int, or predefined type in circuit.h
#               or is 'GSList* ltype' for a list of values of type ltype
#                        ex  { P Nodes GSList* char* }
#               or is complex list of types 
#                        ex { P Mode { A B C ... } A}
#               or portion of a predefined enum type (circuit.h)
#                        ex { P EL_Type Element_type 
#                               {VCVS CCCS VCCS CCVS VCR VCCAP} VCVS}
#
#        then three strings for documentation
#        DocState None -- not in doc, Both -- both ospice and spice para, Opti -- optispice para, Spice -- Spice para
#        DocPos -- integer for position in doc para table 1000 is used for end value
#		 pararange -- used for doc 
#
############################################################################
#                               IMPORTANT
################ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
#
#         Each real physical element *must* have the following parameters first 
#         and in this order!! This will then match Element_Data in InputParse.h
#
#          { P EL_Type Element_type MatrixEl}  { NP Nodes GSList* strn_int}\
    #		   { P Name char* NULL}  { P M double M_DEF} { P S double S_DEF}\
    #          { P Rth double 0} { P Cth double 0}  { P DTEMP double 0 } 
#          { P ExtTnode char* NULL}
#
#            parameter "Name" can be a list {MJ M} in this case the C variable
#            created uses the first name and the other are parsing aliases.
#  
#
set elements \
{\
##
##   Geometery elements
##Vol
     {{{Block RectSolid} LL {{N/A} {N/A}} GuiRequired }\
	  {\
	       { P EL_Type GeoRegion_type GEO_RECT_XYZ "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
	       { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
		   { P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	      { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	      { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P X0 double 0 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
#              Elem specfic   
	       { P DelX double 1 "Delta X" "X extent" "units" OPTIONAL Both 1000 positive No} \
	       { P NX int 10 "NX" "Number of X segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DelY double 1 "Delta Y" "Y extent" "units" OPTIONAL Both 1000 positive No} \
	       { P NY int 10 "NY" "Number of Y segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DelZ double 1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P NZ int 10 "NZ" "Number of Z segments" "N/A" OPTIONAL Both 1000 positive No}\
	   } \
	  GEO_REGION}\
     {{{Quad} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P EL_Type GeoRegion_type GEO_QUAD "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
	       { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
		   { P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	      { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	      { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
#              Elem specfic   
	       { P Atar int 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	      { P {Pos Nodes} GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P NX int 10 "NX" "Number of X segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P NY int 10 "NY" "Number of Y segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P RandDev double 0.0 "RandDev" "Random Deviation for RANDOMXYZ" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DelZ double 1 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P NZ int 10 "NZ" "Number of Z segments" "N/A" OPTIONAL Both 1000 positive No}\
	       # BC statements
	       { P FrontBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {FrontBCParas} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P BackBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {BackBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {TopBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P BotBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {BotBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P LeftBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {LeftBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P RightBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {RightBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       # Padding Control
	      { P FrontPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BackPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P RightPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P LeftPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BottomPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P FrontPadNodes {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BackPadNodes  {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P RightPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P LeftPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BottomPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	   	   { P SurfaceMeshFactor double 1 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	   } \
        GEO_REGION}\
        {{{Triangle} LL {{N/A} {N/A}} GuiOptional }\
        {\
        { P EL_Type GeoRegion_type GEO_TRIANGLE "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
        { P Mode {GeoAndNodes ExtraNodes VolGen VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
        { P ModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
        { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
        { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
        { P Dim GEO_DIM GEO_3D "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
        { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
        { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
        { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
        { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        #              Elem specfic   
        { P Atar int 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
        { P {Pos Nodes} GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
        { P NX int 10 "NX" "Number of X segments" "N/A" OPTIONAL Both 1000 positive No}\
        { P NY int 10 "NY" "Number of Y segments" "N/A" OPTIONAL Both 1000 positive No}\
        { P RandDev double 0.0 "RandDev" "Random Deviation for RANDOMXYZ" "N/A" OPTIONAL Both 1000 positive No}\
        { P DelZ double 1 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
        { P NZ int 10 "NZ" "Number of Z segments" "N/A" OPTIONAL Both 1000 positive No}\
        # BC statements
        { P FrontBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {FrontBCParas} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        { P BackBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {BackBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        { P TopBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {TopBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        { P BotBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {BotBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        { P LeftBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {LeftBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        { P RightBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
        BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P {RightBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
        # Padding Control
        { P FrontPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P BackPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P RightPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P LeftPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P BottomPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P TopPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P FrontPadNodes {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P BackPadNodes  {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P RightPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P LeftPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P BottomPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
        { P TopPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
        } \
	  GEO_REGION}\
        {{{Line} LL {{N/A} {N/A}} GuiOptional }\
            {\
            { P EL_Type GeoRegion_type GEO_LINE "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
            { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
    		{ P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
            { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
            { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
            { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
            { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
            { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
            { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
            { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
            { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            #              Elem specfic   
            { P Atar int 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
            { P {Pos Nodes} GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
            { P NX int 10 "NX" "Number of X segments" "N/A" OPTIONAL Both 1000 positive No}\
            { P NY int 10 "NY" "Number of Y segments" "N/A" OPTIONAL Both 1000 positive No}\
            { P RandDev double 0.0 "RandDev" "Random Deviation for RANDOMXYZ" "N/A" OPTIONAL Both 1000 positive No}\
            { P DelZ double 1 "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
            { P NZ int 10 "NZ" "Number of Z segments" "N/A" OPTIONAL Both 1000 positive No}\
            # BC statements
            { P FrontBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {FrontBCParas} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            { P BackBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {BackBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            { P TopBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {TopBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            { P BotBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {BotBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            { P LeftBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {LeftBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            { P RightBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
            BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P {RightBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
            # Padding Control
            { P FrontPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P BackPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P RightPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P LeftPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P BottomPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P TopPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P FrontPadNodes {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P BackPadNodes  {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P RightPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P LeftPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P BottomPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
            { P TopPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
            } \
GEO_REGION}\
{{{Disc Ring Ellipse} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P EL_Type GeoRegion_type GEO_DISC "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
	       { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
		   { P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	      { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	      { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN RotationZ RotationY RotationX} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
#              Elem specfic   
	       { P DeleteExterior int 0 "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Rad StopRad} double 1 "Radius" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StartRad} double 0 "Start Radius" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StartPhi} double 0 "Start angle " "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StopPhi} double 360 "Stop angle" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RadStep double 1 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
		   { P RadStepHalf double 0 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
		   { P RadStep2 double 0 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PhiSymFact double 1 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P DelZ double 1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P NZ int 10 "NZ" "Number of Z segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P a double 1 "x elipse factor" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P b double 1 "y elipse factor" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P Alpha double 0 "elipse rotation angle" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       # BC statements
	       { P InnerRadBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {InnerRadBCParas} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P OuterRadBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {OuterRadBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {TopBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P BotBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {BotBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMaxBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {PhiMaxBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMinBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {PhiMinBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       # Padding Control
	      { P InnerRadPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P OuterRadPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMinPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMaxPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P InnerRadPadNodes {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P OuterRadPadNodes  {None Interior Exterior Both} None "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMinPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P PhiMaxPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BottomPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopPad {NoPadding SingleRow DoubleRow} NoPadding "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P BottomPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopPadNodes   {None Interior Exterior Both} None  "BC type" "" "" OPTIONAL Both 1000 positive No} \
           { P NX int 10 "NX" "Number of X segments" "N/A" OPTIONAL Both 1000 positive No}\
           { P NY int 10 "NY" "Number of Y segments" "N/A" OPTIONAL Both 1000 positive No}\
	       { P RandDev double 0.0 "RandDev" "Random Deviation for RANDOMXYZ" "N/A" OPTIONAL Both 1000 positive No}\
	   } \
	  GEO_REGION}\
     {{{Sphere Ellipsoid} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P EL_Type GeoRegion_type GEO_SPHERE "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
	       { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
		   { P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	      { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	      { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN RotationZ RotationY RotationX} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
#              Elem specfic   
	       { P DeleteExterior int 0 "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Rad StopRad} double 1 "Radius" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StartRad} double 0 "Start Radius" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StartPhi} double 0 "Start angle " "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StopPhi} double 360 "Stop angle" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StartAlpha} double -90 "Start angle " "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P {StopAlpha} double 90 "Stop angle" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RadStep double 1 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PhiSymFact double 1 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P AlphaSymFact double 1 "Node density" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P a double 1 "x elipse factor" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P b double 1 "y elipse factor" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P c double 1 "y elipse factor" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	   } \
	  GEO_REGION}\
     {{Atar LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P EL_Type GeoRegion_type GEO_ATAR "Block" "Rectangular block" "none" OPTIONAL None 1000 {N/A} No} \
	       { P Mode {GeoAndNodes ExtraNodes Vol VolOutput} GeoAndNodes "Name" "" "" OPTIONAL Both 1000 positive No} \
		   { P VolMode {None Density Net SurfaceFlow SurfaceFlowX SurfaceFlowY SurfaceFlowZ FixedVal FlowPorts PotPorts} None "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P VolModeParas GSList* double NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Material char* NULL "Material" "Material number" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Dim int 3 "Region Dimensionality" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P Pad {NoPadding SingleRow DoubleRow} NoPadding "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P PadNodeMode {None Interior Exterior Both} None "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P ExternalPadFact double 1 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InterFace double 0 "Padding factor" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P InteriorMode {NoGenNodes GenNodes} GenNodes "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P CleanMode {NoClean Clean} Clean "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P BoundaryMode {NoBoundary Boundary} Boundary "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	      { P RefPos GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	      { P RotAngles GSList* double "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \
	       { P RotOrder {000 XYZ YXZ ZYX ZXY} 000 "X0 position" "Min X value" "units" OPTIONAL Both 1000 positive No} \ 
	      { P IntGenMode {Default Radial XYZ RANDOM_XYZ RANDOM} Default "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P DX double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DY double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P DZ double -1 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
	       { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P {Tile} {None Rect Hex RadFixedTheta RadFixedThetaL RadFixedThetaN RotationZ RotationY RotationX} None "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { P {Tile_Geo} {Offset Centered} Offset "Node Mode" "" "N/A" OPTIONAL Both 1000 positive No}\
	       { p Tile_Region_Rad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_Region_InRad double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dX double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dY double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nX int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nY int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_dThetaL double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitR double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_InitTheta double 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_FinalTheta double 360  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nR int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p Tile_nTheta int 0  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { p NodePnts GSList* double3  "X0 position" "Min Z value" "units" OPTIONAL Both 1000 positive No} \
	       { P RandFact double 0 "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P Eq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
#              Elem specfic   
	      { P BlockFile char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       # BC statements
	       { P FrontBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {FrontBCParas} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P BackBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {BackBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P TopBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {TopBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P BotBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {BotBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P LeftBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {LeftBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P RightBC {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {RightBCParas Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P SpecNodesMaterial char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  GEO_REGION}\
#
#    Material statements
#
     {{{Material} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Color GSList* double "Color values" "" "" OPTIONAL Both 1000 positive No} \
	       { P Kappa double 0.5 "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P {Epsilon EpsilonZ} double 11 "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P EpsilonX double 11 "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P EpsilonY double 11 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P {EpsilonComp EpsilonCompZ} double 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P EpsilonCompX double 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P EpsilonCompY double 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ThermalCond double 1 "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ThermalCap double  1 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P EffMass double 1 "Red value" "" "" Optional Both 1000 positive No} \
           { P Density double 1 "Red value" "" "" Optional Both 1000 positive No} \
	   } \
	  MATERIAL}\
#
#    Mesh statement
#
     {{{Mesh Model} PST {{N/A} {N/A}} GuiRequired }\
	  {\
	       # Mode must match EQTYPE in utils.h
	       { P Mode {NO_EQ HEAT_EQ WAVE_EQ MAXWELL_EQ SCHRO_EQ MODESOLVER_EQ} HEAT_EQ "Name" "" "" OPTIONAL Both 1000 positive No} \
	       { P Scale GSList* double "Scale values" "" "" OPTIONAL Both 1000 positive No} \
	       { P Dim int 3 "Geo dimensins" "" "" OPTIONAL Both 1000 positive No} \
	       { P RemoveMultiPnts int 0 "Geo dimensins" "" "" OPTIONAL Both 1000 positive No} \
	       { P Boundary {Stitched Merged Integrated} Integrated  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoFront int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoBack int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoTop int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoBottom int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoRight int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReflGeoLeft int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P MaxMcond double 6  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P Seed int 11  "Red value" "" "" OPTIONAL Both 1000 positive No} \
	       { P GlobalNodeScaling int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
		   { P UseCloudDervs int 0  "Red value" "" "" OPTIONAL Both 1000 positive No} \
    	   { P LooseMergeFactor double 0.2  "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P MesaFactor double 1 "Red value" "" "" Optional Both 1000 positive No} \
           { P MesaFactorCorner double 0 "Red value" "" "" Optional Both 1000 positive No} \
           { P Adaptive int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P ShapeEpi int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P NoSurfaces int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P NumThreads int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P WriteShapesFcts int 1 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P WriteShapesBinary int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
           { P ReadShapesBinary  int 0 "Red value" "" "" OPTIONAL Both 1000 positive No} \
		   { P CheckNodeSym  int 0 "0 no 1 X 2 Y" "" "" OPTIONAL Both 1000 positive No} \
		   { P MeshDensity  double 1 "0 no 1 X 2 Y" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  Mesh}\
	#
	#    Merge Regions
	#
	{{{MergeRegion} LL {{N/A} {N/A}} GuiOptional }\
	 	 {\
		{ P Name char* NULL "Name" "" "" OPTIONAL Both 1000 positive No} \	
		{ P With GSList* char* "Scale values" "" "" OPTIONAL Both 1000 positive No} \
	 } \
	MergeRegion}\
#
#    Boundary Conditions
#
     {{{BC} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Geo {Top Bot Right Left Back Front All} All "BC geo" "" "" OPTIONAL Both 1000 positive No} \
	       { P Type {FixedVal FixedFlow Laplacian Interface Boundary Bulk Trans PML PEC PMC FlowPorts PotPorts}\
		     BC_DEFAULT "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P EMType {EMPerfECondBC EMPerfBCondBC EMReflEBC EMReflBBC EMEAbsBC EMBAbsBC \
			       EMEFixedFlow EMBFixedFlow EMLaplace EMSetValBC EMReflBC EM0 EMPerfCondBC EMAbsBC} \
		     EMPerfECondBC "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {V Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  BC}\
#
#    Boundary Conditions
#
     {{{IC InitCond} LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       # Needs to match InitCondType in Utils.c
	       { P Type {GaussianInitX GaussianInitXY GaussianInitXYZ SinInitX ConstInit ConstXInit SinInitTime CustomDist CustomDistTime Eq} ConstInit "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P {V Values} GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P {Eq RealEq} char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
           { P ImagEq char* NULL "Delta Z" "Z extent" "units" OPTIONAL Both 1000 positive No} \
    	   { P X0 double 0 "X0 position" "Center X value" "units" OPTIONAL Both 1000 positive No} \
	       { P Y0 double 0 "Y0 position" "Center Y value" "units" OPTIONAL Both 1000 positive No} \
	       { P Z0 double 0 "Z0 position" "Center Z value" "units" OPTIONAL Both 1000 positive No} \
	   } \
	  IC}\
#
#    Solver
#
     {{Solver LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Type {Eigen SS Trans FreqDom NoSolve} Eigen "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Delta_t double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P StopTime double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P OutputStep double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P NumTimeSteps int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P MatrixFolder char* NULL "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P Mode {Explicit Implicit MR} Implicit "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ExtSolver {octave matlab optispice modefeed} octave "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ExtProg char* NULL "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P SolFile char* NULL "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P lambda double 10 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P nEffGuess double 1.0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P nEffGuessComp double 0.0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P eigConv double 0.0001 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P nEigs int 6 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P SkipSim int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P PRIMA int 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P ReadNumSolVectors int -1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ReadFullDt double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P ShowMaterialEnergy int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P UseQuadClouds int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P OutputModeSoln int -1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P CalcEHfields int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P SolFiles GSList* char* "BC values" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  Solver}\
#
#    Model Viewer
#
     {{GlView LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Type {NodeMat NodeType DeletedNodes Surface BC Mesh Epi  EpiComp Output CondNum CldNum RegNum} NodeType "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P NodeNormals {NoNormals ElementNormals MaterialNormals BoundaryNormals InterfaceNormals AllNormals} NoNormals "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P ModelView {No Yes} No "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P RadMulti double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P CondLim double 4 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P NumLim double 40 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  GLView}\
#
#    ModeSolver views
     {{ModeSolverView LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Type {Surface Nodes Plane} Surface "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Field {Mag Hx Hy Epi} Mag "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Cmplx {Real Imag Mag} Real "BC type" "" "" OPTIONAL Both 1000 positive No} \
		   { P NodeNormals {NoNormals ElementNormals MaterialNormals BoundaryNormals InterfaceNormals AllNormals} NoNormals "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P nx double 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P ny double 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P RadMulti double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
    	   { P ViewModes GSList* int NULL "Name" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  ModeSolverView}\
#    Heat Solver views
     {{HeatSolverView LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Type {Surface Nodes SurfaceAndNodes MinT MaxT} Surface "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Quantity {Temp Flow} Temp "BC type" "" "" OPTIONAL Both 1000 positive No} \
		   { P NodeNormals {NoNormals ElementNormals MaterialNormals BoundaryNormals InterfaceNormals AllNormals} NoNormals "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P Tmax double -1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P Tmin double -1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P nx double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P ny double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P NodeValue int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P RadMulti double 1 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P NoNodes double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  HeatSolverView}\
#    Heat Solver output
     {{Output LL {{N/A} {N/A}} GuiOptional }\
	  {\
	       { P Type {Temp Hx Hy Var CondNum Epi Pot Psi ThermCond ThermCap VarNum} Temp "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Plot {Time X Y Z XY XZ YZ} Time "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Sort {Xsort Ysort Zsort} Xsort "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Quantity {Mag Flow ddx ddy ddz d2dx2 d2dy2 d2dz2 Del Del2} Mag "BC type" "" "" OPTIONAL Both 1000 positive No} \
		   { P CmplxQuan {Real Imag Mag} Real "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Geo char* NULL "BC type" "" "" OPTIONAL Both 1000 positive No} \
		   { P Mode {Geo Point Line Plane Vol arc} Geo "BC type" "" "" OPTIONAL Both 1000 positive No} \
		   { P {xmin x x0} double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P {xmax length rad} double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P {ymin y y0} double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P ymax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P {zmin z z0} double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P Dir GSList* double "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P {xn n} int 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P yn int 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P zn int 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P rad int 10 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P phiStart int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P phiEnd int 90 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	       { P NodeValue int 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
	   } \
	  Output}\
#    Schro Solver views
    {{SchroSolverView LL {{N/A} {N/A}} GuiOptional }\
        {\
           { P Type {Surface Nodes} Surface "BC type" "" "" OPTIONAL Both 1000 positive No} \
           { P Quantity {Temp Flow} Temp "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P Field {Mag Real Imag Pot} Mag "BC type" "" "" OPTIONAL Both 1000 positive No} \
	       { P xmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P xmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P ymin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P ymax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P zmin double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
           { P zmax double 0 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P nx double 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
		   { P ny double 100 "BC values" "" "" OPTIONAL Both 1000 positive No} \
        } \
    SchroSolverView}\
}


