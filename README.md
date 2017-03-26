excel-mapping
====
# 简介
`excel-mapping`是一个简化`POI`繁杂的API操作，作为常用导入导出为目的对Excel进行操作的组件。`excel-mapping`的目标是简单而强大，通过使用XML或@Annotation作为配置方式，快速完成实际开发工作中复杂而繁琐的Excel操作。
# 功能
## 导入
从File导入 
```java

List<E> read(File file) throws IOException, InvalidFormatException;
```
```java

List<E> read(File file,int sheetIndex) throws IOException, InvalidFormatException;
```
从byte[]导入 
```java
List<E> read(byte[] bytes) throws IOException, InvalidFormatException;

```
```java
List<E> read(byte[] bytes,int sheetIndex) throws IOException, InvalidFormatException;

```
从InputStream导入 
```java
List<E> read(InputStream inputStream) throws IOException, InvalidFormatException;
```
```java
List<E> read(InputStream inputStream,int sheetIndex) throws IOException, InvalidFormatException;
```
从Sheet导入
```java
List<E> read(Sheet sheet);
```
导入目标既可以是一个定义的Bean的List，也可以是Map的List，Map映射的方式只能使用XML配置，JavaBean的映射方式可以使用XML和注解两种配置方式。
## 导出

从List导出
```java
void write(List<E> beans,Workbook workbook);
```
```java
void write(List<E> beans,Workbook workbook,int sheetIndex);
```
```java
void write(List<E> beans,OutputStream outputStream);
```
```java
void write(List<E> beans,OutputStream outputStream,int sheetIndex);
```
```java
void write(List<E> beans,File file);
```
```java
void write(List<E> beans,File file,int sheetIndex);
```
从java.sql.ResultSet导出
```java
void write(ResultSet resultSet,Workbook workbook);
```
```java
void write(ResultSet resultSet,Workbook workbook,int sheetIndex);
```
```java
void write(ResultSet resultSet,OutputStream outputStream);
```
```java
void write(ResultSet resultSet,OutputStream outputStream,int sheetIndex);
```
```java
void write(ResultSet resultSet,File file);
```
```java
void write(ResultSet resultSet,File file,int sheetIndex);
```

## 配置项
### Sheet配置项
- **class(必选)** 映射的JavaBean类全名，或为java.util.Map（映射成List<Map>形式）
- **name(可选)** Sheet的名字，显示在Sheet标签上，默认为空，显示为"Sheet 1"这样的名称
- **enableStyle(可选)** 是否启用样式,默认false（该属性暂未使用，样式的使用会影响性能，在大数据量导出时不建议开启。
- **enableProtect(可选)** 是否启用保护，默认false，与Field的Locked属性配合使用，可试列内容不可编辑。
- **sheetPassword(可选)** Sheet保护的密码，启用保护后可设置密码，不设置则密码为空。
- **defaultColumnWidth(可选)** 设置默认的的列宽，在Field上设置width后可覆盖该项。默认为10
- **version(可选)** 枚举值，可选值hssf和xssf，默认为xssf。
### Field配置项
- **name(必选)** Bean字段名，Map的Key，ResultSet的字段名
- **title(必选)** 在Excel上标题列显示的内容
- **width(可选)** 宽度，默认使用Sheet指定的defaultColumnWidth
- **align(可选)** 暂未启用该配置
- **locked(可选)** 是否锁定，Sheet的enableProtect为true时生效
- **type(可选)** 在使用ResultSet、Map导入时指定该字段可提高转换准确性
- **format(可选)** 根据使用的formatter不同该字段有不同效果，默认的SimpleFieldMappingFormatter使用Groovy表达式指定一个Map，表示从Bean<-->Excel映射时的转换，如数据为boolean型，通过设定format[true:'男':false:'女']转换为Excel后显示为男女
- **fomatter(可选)** 支持自定义的转化方式，通过实现FieldMappingFormatter接口
# Excel版本支持

支持所有Microsoft Excel版本，导出时可指定使用的输出版本，导入时自动识别版本。
版本对应枚举为 ExcelVersionEnum，抽象化为HSSF(.xls)和XSSF(.xlsx)

# 后续开发计划
* 支持更复杂的format，支持日期和数值类型的格式化输出
* 支持多个Sheet的导入及一次将不同数据导出到一个Workbook的多个Sheet
* 更多样式的支持
* 大数据量导出的性能优化
