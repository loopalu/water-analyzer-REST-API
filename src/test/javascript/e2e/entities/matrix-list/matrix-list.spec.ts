/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MatrixListComponentsPage, MatrixListDeleteDialog, MatrixListUpdatePage } from './matrix-list.page-object';

const expect = chai.expect;

describe('MatrixList e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let matrixListUpdatePage: MatrixListUpdatePage;
    let matrixListComponentsPage: MatrixListComponentsPage;
    let matrixListDeleteDialog: MatrixListDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load MatrixLists', async () => {
        await navBarPage.goToEntity('matrix-list');
        matrixListComponentsPage = new MatrixListComponentsPage();
        await browser.wait(ec.visibilityOf(matrixListComponentsPage.title), 5000);
        expect(await matrixListComponentsPage.getTitle()).to.eq('ceApp.matrixList.home.title');
    });

    it('should load create MatrixList page', async () => {
        await matrixListComponentsPage.clickOnCreateButton();
        matrixListUpdatePage = new MatrixListUpdatePage();
        expect(await matrixListUpdatePage.getPageTitle()).to.eq('ceApp.matrixList.home.createOrEditLabel');
        await matrixListUpdatePage.cancel();
    });

    it('should create and save MatrixLists', async () => {
        const nbButtonsBeforeCreate = await matrixListComponentsPage.countDeleteButtons();

        await matrixListComponentsPage.clickOnCreateButton();
        await promise.all([matrixListUpdatePage.setMatrixNameInput('matrixName')]);
        expect(await matrixListUpdatePage.getMatrixNameInput()).to.eq('matrixName');
        await matrixListUpdatePage.save();
        expect(await matrixListUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await matrixListComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last MatrixList', async () => {
        const nbButtonsBeforeDelete = await matrixListComponentsPage.countDeleteButtons();
        await matrixListComponentsPage.clickOnLastDeleteButton();

        matrixListDeleteDialog = new MatrixListDeleteDialog();
        expect(await matrixListDeleteDialog.getDialogTitle()).to.eq('ceApp.matrixList.delete.question');
        await matrixListDeleteDialog.clickOnConfirmButton();

        expect(await matrixListComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
