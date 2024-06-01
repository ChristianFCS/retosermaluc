USE retosermaluc_db;
GO
CREATE TABLE SLUC_T_variable(
	aco_id_asociacion_comuna int
	,componente varchar(500)
	,valor numeric(16,4)
);

GO

CREATE TABLE SLUC_T_formula(
	id bigint identity primary key
	,aco_id_asociacion_comuna int
	,emp_nom_empresa varchar(50)
	,com_nom_comuna varchar(500)
	,sub_nombre_sec varchar(500)
	,opc_tarifaria_id int
	,opc_tarifaria_nombre varchar(500)
	,aca_for_formula_descompuesta varchar(max)
	,car_desc_cargo varchar(max)
	,resultado numeric(16,4)
	,resultado_msg varchar(50)
	,process_date datetime
);

GO

CREATE TYPE SLUC_type_table_variable as TABLE(
	aco_id_asociacion_comuna int
	,componente varchar(500)
	,valor numeric(16,4)
);

GO

CREATE TYPE SLUC_type_table_formula as TABLE(
	aco_id_asociacion_comuna int
	,emp_nom_empresa varchar(50)
	,com_nom_comuna varchar(500)
	,sub_nombre_sec varchar(500)
	,opc_tarifaria_id int
	,opc_tarifaria_nombre varchar(500)
	,aca_for_formula_descompuesta varchar(max)
	,car_desc_cargo varchar(max)
);

GO

CREATE TABLE SLUC_users(
	id int identity primary key
	,username varchar(500)
	,password varchar(2000)
	,active bit
);

GO
--CREAMOS EL USUARIO DE PRUEBA CON EL PASSWORD ENCRIPTADO
INSERT INTO SLUC_users(username,password,active)
VALUES('usuario_admin','$2a$10$6XwY5BlJJiPqQLG3yAl4BOOseZH9WhY61TzowBG9vNitthBm5.Cpq',1);

-------- PROCEDURES ------------------
GO

/*==================================================================================================                   
            
* OBJETIVO          :    Registro de Variables y Formulas                 
            
* ESCRITO POR       :    Condori Soto Christian Fernando         
            
* EMAIL/MOVIL/PHONE:     ccondoris1@gmail.com            
            
* FECHA CREACIÓN    :    2024-05-31            
            
* PROYECTO  :    RETO SERMALUC
            
* MODIFICACIONES    :            
            
*      FECHA         RESPONSABLE                DESCRIPCIÓN DEL CAMBIO            
            
* SINTAXIS DE EJEMPLO:            
            
DECLARE @iDataVariables SLUC_type_table_variable;  
DECLARE @iDataFormulas SLUC_type_table_formula;  
  
INSERT INTO @iDataVariables(aco_id_asociacion_comuna,componente,valor)  
VALUES(19757,'AR',4.027);  

INSERT INTO @iDataFormulas(aco_id_asociacion_comuna
		,emp_nom_empresa 
		,com_nom_comuna 
		,sub_nombre_sec 
		,opc_tarifaria_id 
		,opc_tarifaria_nombre 
		,aca_for_formula_descompuesta 
		,car_desc_cargo )  
VALUES(19757,'A','Alto BíoBío','Pangue 13.2',24,'PEAJE_BT','round((round(CFHS_0*CFHS_ANUAL*(IPC/IPC_BASE);5))*OnOff_SA;3)','Cargo Fijo Mensual'); 
            
       EXEC SLUC_RegistroVariableFormula  @iDataVariables  ,  @iDataFormulas          
            
       
=================================================================================================*/  
CREATE PROCEDURE SLUC_RegistroVariableFormula
	@iDataVariables SLUC_type_table_variable readonly  
	,@iDataFormulas SLUC_type_table_formula readonly  
AS
SET NOCOUNT ON 
BEGIN
	DECLARE @cantRegVariables int, @cantRegFormulas int;
	
	DELETE FROM SLUC_T_variable;
	DELETE FROM SLUC_T_formula;

	INSERT INTO SLUC_T_variable
	SELECT 
	aco_id_asociacion_comuna
	,componente
	,valor
	FROM @iDataVariables;
	
	INSERT INTO SLUC_T_formula
	(aco_id_asociacion_comuna
	,emp_nom_empresa 
	,com_nom_comuna 
	,sub_nombre_sec 
	,opc_tarifaria_id 
	,opc_tarifaria_nombre 
	,aca_for_formula_descompuesta 
	,car_desc_cargo)
	SELECT 
	aco_id_asociacion_comuna
	,emp_nom_empresa 
	,com_nom_comuna 
	,sub_nombre_sec 
	,opc_tarifaria_id 
	,opc_tarifaria_nombre 
	,aca_for_formula_descompuesta 
	,car_desc_cargo 
	FROM @iDataFormulas;
	
	SELECT @cantRegVariables=COUNT(*) from SLUC_T_variable;
	SELECT @cantRegFormulas=COUNT(*) from SLUC_T_formula;

	SELECT @cantRegVariables+@cantRegFormulas as registros_finales;
	
END

GO

/*==================================================================================================                     
              
* OBJETIVO          :    Listar tabla con las formulas procesadas         
              
* ESCRITO POR       :    Condori Soto Christian Fernando           
              
* EMAIL/MOVIL/PHONE:     ccondoris1@gmail.com              
              
* FECHA CREACIÓN    :    2024-05-31              
              
* PROYECTO  :    RETO SERMALUC  
              
* MODIFICACIONES    :              
              
*      FECHA         RESPONSABLE                DESCRIPCIÓN DEL CAMBIO              
              
* SINTAXIS DE EJEMPLO:              
    
       EXEC SLUC_ListarFormulasProcesadas           
              
         
=================================================================================================*/   
CREATE PROCEDURE SLUC_ListarFormulasProcesadas  
AS  
SET NOCOUNT ON   
BEGIN  
 SELECT  
 id   
 ,aco_id_asociacion_comuna   
 ,emp_nom_empresa   
 ,com_nom_comuna   
 ,sub_nombre_sec   
 ,opc_tarifaria_id   
 ,opc_tarifaria_nombre   
 ,aca_for_formula_descompuesta   
 ,car_desc_cargo   
 ,isnull(cast(resultado as varchar(16)),'') as resultado  
 ,isnull(resultado_msg,'') as  resultado_msg  
 ,isnull(process_date,'') as  process_date  
 from SLUC_T_formula  
 order by id;  
END

GO

/*==================================================================================================                     
              
* OBJETIVO          :    Obtener Usuario para el login    
              
* ESCRITO POR       :    Condori Soto Christian Fernando           
              
* EMAIL/MOVIL/PHONE:     ccondoris1@gmail.com              
              
* FECHA CREACIÓN    :    2024-05-31              
              
* PROYECTO  :    RETO SERMALUC  
              
* MODIFICACIONES    :              
              
*      FECHA         RESPONSABLE                DESCRIPCIÓN DEL CAMBIO              
              
* SINTAXIS DE EJEMPLO:              
    
       EXEC SLUC_obtener_usuario 'usuario_admin'           
              
         
=================================================================================================*/   
CREATE PROCEDURE SLUC_obtener_usuario        
  @username VARCHAR(500)        
AS        
BEGIN        
    SELECT        
        id  
  ,username  
  ,password  
  ,active       
    FROM SLUC_users      
    WHERE username = @username;        
END;

GO

CREATE PROCEDURE SLUC_procesoFormula  
 @replaces varchar(max)  
 ,@formula varchar(1000)  
 ,@valores varchar(max)  
 ,@RESULTADO NUMERIC(16,4) output  
 ,@RESULTADO_msg varchar(50) output  
AS        
BEGIN   
BEGIN TRY  
  SET @RESULTADO_msg = 'Procesado Correctamente';  
  DECLARE @SQL_replaced NVARCHAR(MAX);  
  DECLARE @sql_query nvarchar(max) = N'        
        SELECT        
            @SQL_replaced =   
            '+@replaces+''''+REPLACE(@formula,'round','%%%%')+''''+@valores ;    
     
  
  EXECUTE sp_executesql @sql_query,N'@SQL_replaced NVARCHAR(MAX) output',@SQL_replaced=@SQL_replaced OUTPUT  
  
  SET @sql_query = N'        
        SELECT        
            @RESULTADO =   
            '+REPLACE(REPLACE(@SQL_replaced,';',','),'%%%%','round') ;    
  
  EXECUTE sp_executesql @sql_query,N'@RESULTADO NUMERIC(16,4) output',@RESULTADO=@RESULTADO OUTPUT  
END TRY  
BEGIN CATCH  
 SET @RESULTADO_msg = ERROR_MESSAGE();  
 SET @RESULTADO = NULL;  
END CATCH  
   
END

GO

/*==================================================================================================                       
                
* OBJETIVO          :    Procesamiento de Formulas con variables y manejo de errores                    
                
* ESCRITO POR       :    Condori Soto Christian Fernando             
                
* EMAIL/MOVIL/PHONE:     ccondoris1@gmail.com                
                
* FECHA CREACIÓN    :    2024-05-31                
                
* PROYECTO  :    RETO SERMALUC    
                
* MODIFICACIONES    :                
                
*      FECHA         RESPONSABLE                DESCRIPCIÓN DEL CAMBIO                
                
* SINTAXIS DE EJEMPLO:                
      
       EXEC SLUC_ProcesarData             
                
           
=================================================================================================*/     
CREATE PROCEDURE SLUC_ProcesarData    
AS    
SET NOCOUNT ON     
BEGIN    
 WITH     
 replace_previo as(    
  SELECT     
  STRING_AGG('REPLACE(','') as replaces    
  ,STRING_AGG(','''+componente+''','''+CAST(valor as varchar(50))+''')','') WITHIN GROUP (ORDER BY LEN(componente) DESC) as valores    
  ,aco_id_asociacion_comuna    
  FROM SLUC_T_variable    
  GROUP BY aco_id_asociacion_comuna    
 ),    
 cruce_formula as(    
  SELECT    
  V.replaces    
  ,V.valores    
  ,F.*    
  FROM SLUC_T_formula F    
  JOIN replace_previo V on F.aco_id_asociacion_comuna=V.aco_id_asociacion_comuna    
 )    
 select ROW_NUMBER() over(order by aco_id_asociacion_comuna) as nro    
 ,*     
 into #replace_previo    
 from cruce_formula;    
    
 DECLARE @reg int= (select COUNT(*) from #replace_previo);    
 DECLARE @cont int= 1,@replaces varchar(max),@formula varchar(1000),@valores varchar(max),@resultado numeric(16,4);    
 DECLARE @resultado_msg varchar(50);    
 WHILE(@reg>=@cont)    
 BEGIN    
  SELECT    
  @replaces=replaces    
  ,@formula=aca_for_formula_descompuesta    
  ,@valores=valores    
  FROM #replace_previo    
  WHERE nro=@cont    
    
  EXEC SLUC_procesoFormula @replaces,@formula,@valores,@resultado output,@resultado_msg output    
      
  UPDATE #replace_previo    
  SET resultado=@resultado    
  ,resultado_msg=@resultado_msg    
  WHERE nro=@cont    
  
  SET @cont=@cont+1    
 END    
     
  UPDATE F    
  SET F.resultado=rp.resultado    
  ,F.resultado_msg=rp.resultado_msg    
  ,F.process_date=GETDATE()    
  FROM SLUC_T_formula F    
  JOIN #replace_previo rp ON rp.id=F.id;    
    
 select COUNT(*) as cant_reg_updated from #replace_previo    
 drop table #replace_previo    
END

GO


