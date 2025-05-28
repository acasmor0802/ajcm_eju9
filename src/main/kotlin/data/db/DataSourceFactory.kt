package data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DataSourceFactory {

    private const val JDBC_URL = "jdbc:h2:./db/eju9"
    private const val USER = "sa"
    private const val PASSWORD = ""
    private const val DRIVER = "org.h2.Driver"
    private const val MAX_POOL_SIZE = 10

    fun create(): DataSource {
        val config = HikariConfig()

        config.jdbcUrl = JDBC_URL
        config.username = USER
        config.password = PASSWORD
        config.driverClassName = DRIVER
        config.maximumPoolSize = MAX_POOL_SIZE

        return HikariDataSource(config)
    }
}