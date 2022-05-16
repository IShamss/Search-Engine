USE [master]
GO
/****** Object:  Database [searchengine]    Script Date: 16-May-22 11:02:51 PM ******/
CREATE DATABASE [searchengine]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'searchengine', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\searchengine.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'searchengine_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\searchengine_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [searchengine] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [searchengine].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [searchengine] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [searchengine] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [searchengine] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [searchengine] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [searchengine] SET ARITHABORT OFF 
GO
ALTER DATABASE [searchengine] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [searchengine] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [searchengine] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [searchengine] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [searchengine] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [searchengine] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [searchengine] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [searchengine] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [searchengine] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [searchengine] SET  DISABLE_BROKER 
GO
ALTER DATABASE [searchengine] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [searchengine] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [searchengine] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [searchengine] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [searchengine] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [searchengine] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [searchengine] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [searchengine] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [searchengine] SET  MULTI_USER 
GO
ALTER DATABASE [searchengine] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [searchengine] SET DB_CHAINING OFF 
GO
ALTER DATABASE [searchengine] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [searchengine] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [searchengine] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [searchengine] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [searchengine] SET QUERY_STORE = OFF
GO
USE [searchengine]
GO
/****** Object:  Table [dbo].[pages]    Script Date: 16-May-22 11:02:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pages](
	[id] [int] NOT NULL,
	[fileName] [varchar](60) NOT NULL,
	[url] [varchar](150) NOT NULL
) ON [PRIMARY]
GO
USE [master]
GO
ALTER DATABASE [searchengine] SET  READ_WRITE 
GO
