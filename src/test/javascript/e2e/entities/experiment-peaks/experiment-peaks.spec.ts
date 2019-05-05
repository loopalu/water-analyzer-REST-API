/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExperimentPeaksComponentsPage, ExperimentPeaksDeleteDialog, ExperimentPeaksUpdatePage } from './experiment-peaks.page-object';

const expect = chai.expect;

describe('ExperimentPeaks e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let experimentPeaksUpdatePage: ExperimentPeaksUpdatePage;
    let experimentPeaksComponentsPage: ExperimentPeaksComponentsPage;
    let experimentPeaksDeleteDialog: ExperimentPeaksDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExperimentPeaks', async () => {
        await navBarPage.goToEntity('experiment-peaks');
        experimentPeaksComponentsPage = new ExperimentPeaksComponentsPage();
        await browser.wait(ec.visibilityOf(experimentPeaksComponentsPage.title), 5000);
        expect(await experimentPeaksComponentsPage.getTitle()).to.eq('ceApp.experimentPeaks.home.title');
    });

    it('should load create ExperimentPeaks page', async () => {
        await experimentPeaksComponentsPage.clickOnCreateButton();
        experimentPeaksUpdatePage = new ExperimentPeaksUpdatePage();
        expect(await experimentPeaksUpdatePage.getPageTitle()).to.eq('ceApp.experimentPeaks.home.createOrEditLabel');
        await experimentPeaksUpdatePage.cancel();
    });

    it('should create and save ExperimentPeaks', async () => {
        const nbButtonsBeforeCreate = await experimentPeaksComponentsPage.countDeleteButtons();

        await experimentPeaksComponentsPage.clickOnCreateButton();
        await promise.all([
            experimentPeaksUpdatePage.setPeakNumberInput('5'),
            experimentPeaksUpdatePage.setPeakStartInput('5'),
            experimentPeaksUpdatePage.setPeakEndInput('5'),
            experimentPeaksUpdatePage.setPeakHighestInput('5'),
            experimentPeaksUpdatePage.setPeakAreaInput('5'),
            experimentPeaksUpdatePage.setAnalyteConcentrationInput('5'),
            experimentPeaksUpdatePage.analyteSelectLastOption(),
            experimentPeaksUpdatePage.experimentAnalysisSelectLastOption()
        ]);
        expect(await experimentPeaksUpdatePage.getPeakNumberInput()).to.eq('5');
        expect(await experimentPeaksUpdatePage.getPeakStartInput()).to.eq('5');
        expect(await experimentPeaksUpdatePage.getPeakEndInput()).to.eq('5');
        expect(await experimentPeaksUpdatePage.getPeakHighestInput()).to.eq('5');
        expect(await experimentPeaksUpdatePage.getPeakAreaInput()).to.eq('5');
        expect(await experimentPeaksUpdatePage.getAnalyteConcentrationInput()).to.eq('5');
        await experimentPeaksUpdatePage.save();
        expect(await experimentPeaksUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await experimentPeaksComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ExperimentPeaks', async () => {
        const nbButtonsBeforeDelete = await experimentPeaksComponentsPage.countDeleteButtons();
        await experimentPeaksComponentsPage.clickOnLastDeleteButton();

        experimentPeaksDeleteDialog = new ExperimentPeaksDeleteDialog();
        expect(await experimentPeaksDeleteDialog.getDialogTitle()).to.eq('ceApp.experimentPeaks.delete.question');
        await experimentPeaksDeleteDialog.clickOnConfirmButton();

        expect(await experimentPeaksComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
