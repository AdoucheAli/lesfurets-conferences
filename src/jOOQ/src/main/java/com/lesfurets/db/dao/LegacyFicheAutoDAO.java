package com.lesfurets.db.dao;

import com.lesfurets.model.FicheAuto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LegacyFicheAutoDAO extends DAO {

    private static final String COL_ID = "id";
    private static final String COL_OFFRE_UID = "offre_uid";
    private static final String COL_CREATION_DATE = "creation_date";
    private static final String COL_CODE_POSTAL = "code_postal";
    private static final String COL_EMAIL = "email";
    private static final String COL_CODE_SRA = "code_sra";
    private static final String COL_STATUT = "statut";

    private static final String TABLE_FICHES_AUTO = "FICHES_AUTO";

    private final String SELECT_ONE = "SELECT "
            + COL_ID + ", "
            + COL_OFFRE_UID + ", "
            + COL_CREATION_DATE + ", "
            + COL_CODE_POSTAL + ", "
            + COL_EMAIL + ", "
            + COL_CODE_SRA + ", "
            + COL_STATUT
            + " FROM " + TABLE_FICHES_AUTO
            + " WHERE " + COL_OFFRE_UID + " = ? "
            + " AND " + COL_STATUT + " > ? "
            + " ORDER BY statut DESC ";

    public LegacyFicheAutoDAO(Connection connection) {
        super(connection);
    }

    public List<FicheAuto> selectRecentFiches(String offreUid, int statut) throws Exception {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ONE)) {
            ps.setString(1, offreUid);
            ps.setInt(2, statut);
            try (ResultSet rs = ps.executeQuery()) {
                List<FicheAuto> ficheAutos = new ArrayList<>();
                while (rs.next()) {
                    ficheAutos.add(
                            new FicheAuto(
                                    rs.getInt(COL_ID),
                                    rs.getString(COL_OFFRE_UID),
                                    LocalDate.parse(rs.getString(COL_CREATION_DATE), DateTimeFormatter.ISO_DATE),
                                    rs.getString(COL_CODE_POSTAL),
                                    rs.getString(COL_EMAIL),
                                    rs.getString(COL_CODE_SRA),
                                    rs.getInt(COL_STATUT)));
                }
                return ficheAutos;
            }
        }
    }

}