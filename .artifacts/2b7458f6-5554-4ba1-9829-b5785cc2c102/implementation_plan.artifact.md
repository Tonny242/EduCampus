# Implementation Plan - Layout and Button Fixes

This plan addresses the spacing issues between blue buttons and the visibility/rendering issues of the Logout and Return buttons.

## User Review Required

> [!IMPORTANT]
> - Spacing will be added between all buttons on the dashboard using standard margins.
> - The `Secondary` button style (used for "Se déconnecter" and "Retour au Dashboard") will be fixed to ensure visibility. I will replace the problematic theme overlay approach with a direct `backgroundTint`.

## Proposed Changes

### [1. Theme & Styles]

#### [MODIFY] [themes.xml](file:///C:/Users/PONGO/AndroidStudioProjects/EduCampus/app/src/main/res/values/themes.xml)
- Simplify `Widget.EduCampus.Button.Secondary` by using `backgroundTint` instead of `materialThemeOverlay`. This avoids rendering issues (like the magenta blocks) when an overlay is incomplete.
- Ensure all custom buttons have consistent padding.

### [2. Layout Spacing]

#### [MODIFY] [activity_dashboard.xml](file:///C:/Users/PONGO/AndroidStudioProjects/EduCampus/app/src/main/res/layout/activity_dashboard.xml)
- Add `android:layout_marginTop="@dimen/spacing_small"` to all student action buttons (`btnEmploi`, `btnNotes`, etc.) to prevent them from touching.

#### [MODIFY] [activity_admin_dashboard.xml](file:///C:/Users/PONGO/AndroidStudioProjects/EduCampus/app/src/main/res/layout/activity_admin_dashboard.xml)
- Ensure the Logout button at the bottom also uses the fixed style and has proper spacing.

### [3. Component Visibility]

#### [VERIFY] Other Activities
- Check `activity_cours.xml`, `activity_emploi.xml`, `activity_notes.xml`, `activity_absences.xml`, `activity_annonces.xml`, `activity_profil.xml`, and `activity_cours_detail.xml`.
- Ensure the "Retour" buttons (which use the Secondary style) are correctly positioned and visible.

## Verification Plan

### Automated Tests
- Run Gradle build to ensure no resource errors.

### Manual Verification
- Verify the Dashboard: Buttons should be separated.
- Verify "Se déconnecter": Should be green/teal (secondary color) and clearly visible.
- Verify "Retour au Dashboard" in various screens: Should be visible and styled correctly.
