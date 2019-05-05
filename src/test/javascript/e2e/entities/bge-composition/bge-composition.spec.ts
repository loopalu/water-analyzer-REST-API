/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BGECompositionComponentsPage, BGECompositionDeleteDialog, BGECompositionUpdatePage } from './bge-composition.page-object';

const expect = chai.expect;

describe('BGEComposition e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let bGECompositionUpdatePage: BGECompositionUpdatePage;
    let bGECompositionComponentsPage: BGECompositionComponentsPage;
    let bGECompositionDeleteDialog: BGECompositionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BGECompositions', async () => {
        await navBarPage.goToEntity('bge-composition');
        bGECompositionComponentsPage = new BGECompositionComponentsPage();
        await browser.wait(ec.visibilityOf(bGECompositionComponentsPage.title), 5000);
        expect(await bGECompositionComponentsPage.getTitle()).to.eq('ceApp.bGEComposition.home.title');
    });

    it('should load create BGEComposition page', async () => {
        await bGECompositionComponentsPage.clickOnCreateButton();
        bGECompositionUpdatePage = new BGECompositionUpdatePage();
        expect(await bGECompositionUpdatePage.getPageTitle()).to.eq('ceApp.bGEComposition.home.createOrEditLabel');
        await bGECompositionUpdatePage.cancel();
    });

    it('should create and save BGECompositions', async () => {
        const nbButtonsBeforeCreate = await bGECompositionComponentsPage.countDeleteButtons();

        await bGECompositionComponentsPage.clickOnCreateButton();
        await promise.all([
            bGECompositionUpdatePage.setBackgroundElectrolyteInput('backgroundElectrolyte'),
            bGECompositionUpdatePage.setBgeConcentrationInput('5'),
            bGECompositionUpdatePage.methodSelectLastOption()
        ]);
        expect(await bGECompositionUpdatePage.getBackgroundElectrolyteInput()).to.eq('backgroundElectrolyte');
        expect(await bGECompositionUpdatePage.getBgeConcentrationInput()).to.eq('5');
        await bGECompositionUpdatePage.save();
        expect(await bGECompositionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await bGECompositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BGEComposition', async () => {
        const nbButtonsBeforeDelete = await bGECompositionComponentsPage.countDeleteButtons();
        await bGECompositionComponentsPage.clickOnLastDeleteButton();

        bGECompositionDeleteDialog = new BGECompositionDeleteDialog();
        expect(await bGECompositionDeleteDialog.getDialogTitle()).to.eq('ceApp.bGEComposition.delete.question');
        await bGECompositionDeleteDialog.clickOnConfirmButton();

        expect(await bGECompositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
