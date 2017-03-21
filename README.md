excel-mapping
====
# 简介
`excel-mapping`是一个简化`POI`繁杂的API操作，作为常用导入导出为目的对Excel进行操作的组件。`excel-mapping`的目标是简单而强大，通过使用XML或@Annotation作为配置方式，快速完成实际开发工作中复杂而繁琐的Excel操作。
# 功能
## 导入
从File导入 

从byte[]导入 

从InputStream导入 

从Sheet导入

导入目标既可以是一个定义的Bean的List，也可以是Map的List
## 导出

从Bean的List导出

从Map的List导出

从java.sql.ResultSet导出

## 配置项

# Excel版本支持

支持所有MicroSoft Excel版本，导出时可指定使用的输出版本，导入时自动识别版本。
版本对应枚举为 ExcelVersionEnum

# 后续开发计划
* 支持更复杂的format，支持日期和数值类型的格式化输出
* 支持多个Sheet的导入及一次将不同数据导出到一个Workbook的多个Sheet
* 更多样式的支持
* 大数据量导出的性能优化
