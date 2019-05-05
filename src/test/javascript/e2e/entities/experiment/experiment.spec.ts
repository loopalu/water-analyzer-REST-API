/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExperimentComponentsPage, ExperimentDeleteDialog, ExperimentUpdatePage } from './experiment.page-object';

const expect = chai.expect;

describe('Experiment e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let experimentUpdatePage: ExperimentUpdatePage;
    let experimentComponentsPage: ExperimentComponentsPage;
    let experimentDeleteDialog: ExperimentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Experiments', async () => {
        await navBarPage.goToEntity('experiment');
        experimentComponentsPage = new ExperimentComponentsPage();
        await browser.wait(ec.visibilityOf(experimentComponentsPage.title), 5000);
        expect(await experimentComponentsPage.getTitle()).to.eq('ceApp.experiment.home.title');
    });

    it('should load create Experiment page', async () => {
        await experimentComponentsPage.clickOnCreateButton();
        experimentUpdatePage = new ExperimentUpdatePage();
        expect(await experimentUpdatePage.getPageTitle()).to.eq('ceApp.experiment.home.createOrEditLabel');
        await experimentUpdatePage.cancel();
    });

    it('should create and save Experiments', async () => {
        const nbButtonsBeforeCreate = await experimentComponentsPage.countDeleteButtons();

        await experimentComponentsPage.clickOnCreateButton();
        await promise.all([
            experimentUpdatePage.setExperimentTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            experimentUpdatePage.setExperimentTypeInput('experimentType'),
            experimentUpdatePage.methodSelectLastOption()
        ]);
        expect(await experimentUpdatePage.getExperimentTimeInput()).to.contain('2001-01-01T02:30');
        expect(await experimentUpdatePage.getExperimentTypeInput()).to.eq('experimentType');
        await experimentUpdatePage.save();
        expect(await experimentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await experimentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Experiment', async () => {
        const nbButtonsBeforeDelete = await experimentComponentsPage.countDeleteButtons();
        await experimentComponentsPage.clickOnLastDeleteButton();

        experimentDeleteDialog = new ExperimentDeleteDialog();
        expect(await experimentDeleteDialog.getDialogTitle()).to.eq('ceApp.experiment.delete.question');
        await experimentDeleteDialog.clickOnConfirmButton();

        expect(await experimentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
