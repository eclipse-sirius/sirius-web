/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  RepresentationLoadingIndicator,
  Selection,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  FormBasedView,
  FormContext,
  FormHandle,
  GQLForm,
  GQLFormRefreshedEventPayload,
  Group,
} from '@eclipse-sirius/sirius-components-forms';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, MutableRefObject, useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { SynchronizationButton } from '../SynchronizationButton';
import { RelatedElementsViewState } from './RelatedElementsView.types';
import { useRelatedElementsViewHandle } from './useRelatedElementsViewHandle';
import { useRelatedElementsViewSubscription } from './useRelatedElementsViewSubscription';
import { GQLRelatedElementsEventPayload } from './useRelatedElementsViewSubscription.types';

const useRelatedElementsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  view: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    gap: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    alignItems: 'center',
    borderBottomColor: theme.palette.divider,
  },
  content: {
    overflow: 'auto',
  },
}));

const isFormRefreshedEventPayload = (
  payload: GQLRelatedElementsEventPayload
): payload is GQLFormRefreshedEventPayload => payload && payload.__typename === 'FormRefreshedEventPayload';

export const RelatedElementsView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id, editingContextId, readOnly }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const [state, setState] = useState<RelatedElementsViewState>({
      form: null,
      objectIds: [],
      pinned: false,
    });

    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'relatedElementsView' });

    const applySelection = (selection: Selection) => {
      const newObjetIds = selection.entries.map((entry) => entry.id);
      setState((prevState) => ({
        ...prevState,
        objectIds: newObjetIds,
      }));
    };

    const formBasedViewRef: MutableRefObject<FormHandle | null> = useRef<FormHandle | null>(null);
    useRelatedElementsViewHandle(id, formBasedViewRef, applySelection, ref);

    const { selection } = useSelection();
    useEffect(() => {
      if (!state.pinned) {
        applySelection(selection);
      }
    }, [selection, state.pinned]);

    const skip = state.objectIds.length === 0;
    const { payload, complete, loading } = useRelatedElementsViewSubscription(editingContextId, state.objectIds, skip);
    useEffect(() => {
      if (isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, form: payload.form }));
      }
    }, [payload]);

    const { classes } = useRelatedElementsViewStyles();

    const extractFirstGroup = (editingContextId: string, form: GQLForm, readOnly: boolean): JSX.Element => {
      const group = form.pages[0]?.groups[0];
      if (group) {
        return (
          <div className={classes.content}>
            <Group editingContextId={editingContextId} formId={form.id} readOnly={readOnly} group={group} />
          </div>
        );
      } else {
        return <div className={classes.content} />;
      }
    };

    const toolbar = (
      <SynchronizationButton
        pinned={state.pinned && !skip}
        onClick={() => setState((prevState) => ({ ...prevState, pinned: !prevState.pinned }))}
      />
    );

    let contents: JSX.Element = <></>;

    if (complete || skip) {
      contents = (
        <div className={classes.idle}>
          <Typography variant="subtitle2">{t('noObjectSelected')}</Typography>
        </div>
      );
    } else {
      contents = (
        <Box
          sx={{
            display: 'grid',
            gridTemplateRows: '1fr',
            gridTemplateColumns: '1fr',
          }}
          data-representation-kind="form-related-elements">
          {!state.form || loading ? (
            <Box sx={{ gridRow: '1', gridColumn: '1' }}>
              <RepresentationLoadingIndicator />
            </Box>
          ) : null}
          {state.form ? (
            <Box sx={{ gridRow: '1', gridColumn: '1' }}>
              <FormContext.Provider
                value={{
                  payload: payload,
                }}>
                <FormBasedView
                  editingContextId={editingContextId}
                  form={state.form}
                  initialSelectedPageId={null}
                  readOnly={readOnly}
                  postProcessor={extractFirstGroup}
                  ref={formBasedViewRef}
                />
              </FormContext.Provider>
            </Box>
          ) : null}
        </Box>
      );
    }

    return (
      <div className={classes.view}>
        <div className={classes.toolbar}>{toolbar}</div>
        <div className={classes.content}>{contents}</div>
      </div>
    );
  }
);
